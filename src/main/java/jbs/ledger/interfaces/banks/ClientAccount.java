package jbs.ledger.interfaces.banks;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import java.util.UUID;

/**
 * An account that holds clients' assets.
 * @param <A> Type of asset to hold.
 */
public interface ClientAccount<A extends Asset> extends Unique {
    UUID getClientId();
    AbstractPortfolio<A> getContent();
}
