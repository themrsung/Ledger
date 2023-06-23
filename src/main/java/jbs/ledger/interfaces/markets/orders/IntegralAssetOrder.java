package jbs.ledger.interfaces.markets.orders;

import jbs.ledger.interfaces.assets.IntegralAsset;

/**
 * An order to trade integral assets.
 * @param <A> An integral asset.
 */
public interface IntegralAssetOrder<A extends IntegralAsset> extends Order<A> {

}
