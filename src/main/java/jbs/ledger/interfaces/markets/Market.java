package jbs.ledger.interfaces.markets;

import jbs.ledger.interfaces.common.Unique;

/**
 * A market for trading assets.
 * Orders are automatically processed.
 * @param <A> The exact asset trade in this market. Quantity of this asset will be the contract size.
 */
public interface Market<A> extends Unique {
    A getAsset();

    String getCurrency();
    double getUnitPrice(A asset);
    double getValue(A asset);



}
