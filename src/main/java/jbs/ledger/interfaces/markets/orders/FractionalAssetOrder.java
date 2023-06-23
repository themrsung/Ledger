package jbs.ledger.interfaces.markets.orders;

import jbs.ledger.interfaces.assets.FractionalAsset;
import jbs.ledger.interfaces.markets.Market;

/**
 * An order to trade a fractional asset.
 * @param <A> A fractional asset.
 */
public interface FractionalAssetOrder<A extends FractionalAsset> extends Order<A> {
    @Override
    default void onFulfilled(Market<A> market, double price, long quantity) {

    }
}
