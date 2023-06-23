package jbs.ledger.interfaces.assets;

/**
 * An asset
 */
public interface Asset {
    String getSymbol();

    default boolean isStackable(Asset asset) {
        return this.getSymbol().equalsIgnoreCase(asset.getSymbol());
    }

    Asset copy();
}
