package jbs.ledger.interfaces.markets.orders;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.markets.OrderType;

import java.util.Date;

/**
 * An order to trade an asset.
 * @param <A> The asset to trade.
 */
public interface Order<A extends Asset> extends Unique {
    Economic getSender();

    OrderType getType();
    Date getDate();
    double getPrice();
    long getQuantity();
    default double getVolume() {
        return getPrice() * getQuantity();
    }

    void setPrice(double price);

    void onFulfilled(Market<A> market, double price, long quantity);
}
