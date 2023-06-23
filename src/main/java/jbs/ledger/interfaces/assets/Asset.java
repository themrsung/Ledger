package jbs.ledger.interfaces.assets;

import jbs.ledger.interfaces.common.Economic;

public interface Asset {
    Economic getHolder();
    void setHolder(Economic holder);
    String getSymbol();

    default boolean isStackable(Asset asset) {
        return this.getSymbol().equalsIgnoreCase(asset.getSymbol());
    }

    Asset copy();
}
