package jbs.ledger.interfaces.markets;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.markets.orders.Order;

import java.util.ArrayList;

/**
 * A market for trading assets.
 * Orders are automatically processed.
 * @param <A> The exact asset trade in this market. Quantity of this asset will be the contract size.
 */
public interface Market<A extends Asset> extends Unique {
    A getAsset();

    ArrayList<Order<A>> getOrders();

    String getCurrency();
    double getUnitPrice(A asset);
    double getValue(A asset);
}
