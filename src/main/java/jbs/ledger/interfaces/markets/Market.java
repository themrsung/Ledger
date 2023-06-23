package jbs.ledger.interfaces.markets;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.orders.Order;

import java.util.ArrayList;

/**
 * A market for trading one type of asset.
 * Orders are automatically processed.
 * Quantity is integral.
 * @param <A> The exact asset trade in this market. Quantity of this asset will be the contract size.
 */
public interface Market<A extends Asset> extends Economic {
    A getAsset();

    /**
     * Gets all orders submitted to this exchange
     * @return Returns a copied list
     */
    ArrayList<Order<A>> getOrders();
    void placeOrder(Order<A> order);
    boolean cancelOrder(Order<A> order);

    String getCurrency();
    double getPrice();
    void setPrice(double price);

    default ArrayList<Order<A>> getBuyOrders() {
        ArrayList<Order<A>> orders = getOrders();

        orders.removeIf(o -> !o.getType().isBuy());

        return orders;
    }

    default ArrayList<Order<A>> getSellOrders() {
        ArrayList<Order<A>> orders = getOrders();

        orders.removeIf(o -> o.getType().isBuy());

        return orders;
    }

    default void processOrders(Economic exchange) {
        ArrayList<Order<A>> buyOrders = getBuyOrders();
        ArrayList<Order<A>> sellOrders = getSellOrders();

        buyOrders.removeIf(o -> o.getQuantity() <= 0);
        sellOrders.removeIf(o -> o.getQuantity() <= 0);

        buyOrders.sort(((o1, o2) -> o1.getDate().compareTo(o2.getDate())));
        sellOrders.sort(((o1, o2) -> o1.getDate().compareTo(o2.getDate())));

        double highestSellPrice = getHighestSellOrderPrice();
        for (Order<A> o : buyOrders) {
            if (o.getType().isMarket()) {
                o.setPrice(highestSellPrice);
            }
        }

        double lowestBuyPrice = getLowestBuyOrderPrice();
        for (Order<A> o : sellOrders) {
            if (o.getType().isMarket()) {
                o.setPrice(lowestBuyPrice);
            }
        }

        buyOrders.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        sellOrders.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));

        for (Order<A> bo : buyOrders) {
            for (Order<A> so : sellOrders) {
                if (bo.getPrice() >= so.getPrice()) {
                    double p = bo.getDate().before(so.getDate()) ? bo.getPrice() : so.getPrice();
                    long q = Math.min(bo.getQuantity(), so.getQuantity());

                    bo.onFulfilled(this, p, q);
                    so.onFulfilled(this, p, q);

                    setPrice(p);
                }
            }
        }

        buyOrders.removeIf(o -> o.getQuantity() <= 0);
        sellOrders.removeIf(o -> o.getQuantity() <= 0);
    }

    default double getHighestSellOrderPrice() {
        double p = 0d;

        for (Order<A> o : getSellOrders()) {
            if (o.getPrice() > p) {
                p = o.getPrice();
            }
        }

        return p;
    }

    default double getLowestBuyOrderPrice() {
        double p = Double.MAX_VALUE;

        for (Order<A> o : getBuyOrders()) {
            if (o.getPrice() < p) {
                p = o.getPrice();
            }
        }

        return p;
    }
}
