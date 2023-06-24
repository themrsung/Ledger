package jbs.ledger.interfaces.assets;

import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.types.assets.AssetType;

/**
 * An asset
 */
public interface Asset extends Symbolic {
    default boolean isStackable(Asset asset) {
        return this.getSymbol().equalsIgnoreCase(asset.getSymbol());
    }

    Asset copy();

    AssetType getType();
}
