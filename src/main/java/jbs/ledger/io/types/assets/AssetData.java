package jbs.ledger.io.types.assets;

import jbs.ledger.types.assets.AssetType;

public abstract class AssetData {
    public AssetData() {}
    public AssetType type;

    public String symbol;
}
