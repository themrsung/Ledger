package jbs.ledger.interfaces.orders;

import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;
import java.util.Date;

public interface Order<A extends Asset> extends Unique {
    OrderType getType();
    Economic getSender();
    Date getDate();

    double getPrice();
    void setPrice(double price);

    long getQuantity();
    void setQuantity(long quantity);

    default double getVolume() {
        return getPrice() * getQuantity();
    }

    @Nullable
    UniqueNote<Cash> getCashCollateral();
    @Nullable
    UniqueNote<A> getAssetCollateral();

    /**
     * Reduces quantity of order and unregisters the portion of collateral used.
     * @param market Market this order was place to.
     * @param price Price of fulfillment.
     * @param quantity Quantity of fulfillment.
     */
    default void onFulfilled(Market<A> market, double price, long quantity) {
        float fulfillmentRatio = (float) quantity / getQuantity();
        double expectedVolume = getVolume() * fulfillmentRatio;

        if (getCashCollateral() != null) getCashCollateral().getDelivery().removeBalance(expectedVolume);
        if (getAssetCollateral() != null) getAssetCollateral().removeQuantity(quantity);

        if (getQuantity() <= 0L) {
            unregisterAssetCollateral(market);
            unregisterCashCollateral(market);
        }
    }

    default void unregisterCashCollateral(Market<A> market) {
        market.getExchange().getNotes().remove(getCashCollateral());
    }

    void unregisterAssetCollateral(Market<A> market);
}
