package jbs.ledger.interfaces.markets;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.types.markets.MarketTickData;
import org.bukkit.Bukkit;

import javax.annotation.Nonnegative;
import java.util.ArrayList;

/**
 * A market for trading assets.
 * Price can be negative.
 * @param <A> Type of asset to trade.
 */
public interface Market<A extends Asset> extends Symbolic, Unique {
    Economic getExchange();
    String getCurrency();
    A getUnitAsset();

    /**
     * Gets all orders submitted to this market.
     * @return Copied list of orders.
     */
    ArrayList<Order<A>> getOrders();

    default ArrayList<Order<A>> getBuyOrders() {
        ArrayList<Order<A>> orders = new ArrayList<>();

        for (Order<A> o : getOrders()) {
            if (o.getType().isBuy()) {
                orders.add(o);
            }
        }

        return orders;
    }

    default ArrayList<Order<A>> getSellOrders() {
        ArrayList<Order<A>> orders = new ArrayList<>();

        for (Order<A> o : getOrders()) {
            if (!o.getType().isBuy()) {
                orders.add(o);
            }
        }

        return orders;
    }

    /**
     * Current price of the asset being traded.
     * @return Price denoted in the currency of this market.
     */
    double getPrice();
    void setPrice(double p);

    /**
     * Accumulative volume since last period reset.
     * Higher volume usually mean fairer prices.
     * @return Volume
     */
    long getVolume();
    void setVolume(long volume);
    void addVolume(long delta);

    /**
     * Minimum tick size of this market.
     * Defaults to 0.1
     * @return Tick size
     */
    @Nonnegative
    default double getTickSize() {
        return 0.1d;
    }

    /**
     * Sets the tick size of this market.
     * @param size Tick size
     */
    void setTickSize(@Nonnegative double size);

    /**
     * Starts a new market period.
     * Called once per day by default.
     */
    default void startNewMarketPeriod() {
        setVolume(0L);

        for (Order<A> o : getOrders()) {
            cancelOrder(o);
        }
    }

    /**
     * Submits order to market.
     * Price will be adjusted to the nearest tick in favor of sender.
     * Setting the price of a market order is unnecessary.
     * @param order Order to submit.
     */
    void placeOrder(Order<A> order);

    /**
     * Force removes order from market.
     * Does not unregister collateral.
     * @param order Order to remove.
     * @return Whether order was removed.
     */
    boolean removeOrder(Order<A> order);

    /**
     * Safely cancels order from market.
     * @param order Order to cancel.
     * @return Whether order was removed.
     */
    default boolean cancelOrder(Order<A> order) {
        order.unregisterCashCollateral(this);
        order.unregisterAssetCollateral(this);
        return removeOrder(order);
    }

    default void processOrders() {
        ArrayList<Order<A>> buyOrders = getBuyOrders();
        ArrayList<Order<A>> sellOrders = getSellOrders();

        // Clean orders with 0 quantity

        for (Order<A> bo : new ArrayList<>(buyOrders)) {
            if (bo.getQuantity() <= 0L) {
                cancelOrder(bo);
            }
        }

        for (Order<A> so : new ArrayList<>(sellOrders)) {
            if (so.getQuantity() <= 0L) {
                cancelOrder(so);
            }
        }

        // Sort by date ascending

        buyOrders.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        sellOrders.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        // Reprice market orders to their worst possible fulfillment price

        double highestSellPrice = getHighestSellPrice();
        for (Order<A> bo : buyOrders) {
            if (bo.getType().isMarket()) {
                bo.setPrice(highestSellPrice + Double.MIN_VALUE);
            }
        }

        double lowestBuyPrice = getLowestBuyPrice();
        for (Order<A> so : sellOrders) {
            if (so.getType().isMarket()) {
                so.setPrice(lowestBuyPrice - Double.MIN_VALUE);
            }
        }

        // Sort by worst price

        buyOrders.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        sellOrders.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));

        // Match orders

        for (Order<A> bo : buyOrders) {
            for (Order<A> so : sellOrders) {
                if (bo.getPrice() >= so.getPrice()) {
                    double p = bo.getDate().before(so.getDate()) ? bo.getPrice() : so.getPrice();
                    long q = Math.min(bo.getQuantity(), so.getQuantity());

                    bo.onFulfilled(this, p, q);
                    so.onFulfilled(this, p, q);

                    setPrice(p);
                    addVolume(q);
                }
            }
        }

        // Clean orders with 0 quantity

        for (Order<A> bo : new ArrayList<>(buyOrders)) {
            if (bo.getQuantity() <= 0L) {
                cancelOrder(bo);
            }
        }

        for (Order<A> so : new ArrayList<>(sellOrders)) {
            if (so.getQuantity() <= 0L) {
                cancelOrder(so);
            }
        }
    }

    /**
     * Gets the highest price of this asset.
     * @return Highest sell order price.
     */
    default double getHighestSellPrice() {
        double p = 0d;

        for (Order<A> o : getSellOrders()) {
            if (o.getPrice() > p) {
                p = o.getPrice();
            }
        }

        return p;
    }

    /**
     * Gets the lowest price of this asset.
     * @return Lowest buy order price.
     */
    default double getLowestBuyPrice() {
        double p = Double.MAX_VALUE;

        for (Order<A> o : getBuyOrders()) {
            if (o.getPrice() < p) {
                p = o.getPrice();
            }
        }

        return p != Double.MAX_VALUE ? p : 0d;
    }

    /**
     * Gets structured list of buy order data.
     * @return Returns sorted list of buy orders. Sorted by price descending.
     */
    default ArrayList<MarketTickData> getBuyTicks() {
        ArrayList<MarketTickData> ticks = new ArrayList<>();

        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<Long> volume = new ArrayList<>();

        for (Order<A> o : getBuyOrders()) {
            if (!prices.contains(o.getPrice())) {
                prices.add(o.getPrice());
                volume.add(o.getQuantity());
            } else {
                int index = prices.indexOf(o.getPrice());
                volume.set(index, volume.get(index) + o.getQuantity());
            }
        }

        for (int i = 0; i < prices.size(); i++) {
            ticks.add(new MarketTickData(
                    prices.get(i),
                    volume.get(i)
            ));
        }

        ticks.sort((t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));

        return ticks;
    }

    /**
     * Gets structured list os sell order data.
     * @return Returns sorted list of sell orders. Sorted by price ascending.
     */
    default ArrayList<MarketTickData> getSellTicks() {
        ArrayList<MarketTickData> ticks = new ArrayList<>();

        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<Long> volume = new ArrayList<>();

        for (Order<A> o : getSellOrders()) {
            if (!prices.contains(o.getPrice())) {
                prices.add(o.getPrice());
                volume.add(o.getQuantity());
            } else {
                int index = prices.indexOf(o.getPrice());
                volume.set(index, volume.get(index) + o.getQuantity());
            }
        }

        for (int i = 0; i < prices.size(); i++) {
            ticks.add(new MarketTickData(
                    prices.get(i),
                    volume.get(i)
            ));
        }

        ticks.sort((t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));

        return ticks;
    }
}
