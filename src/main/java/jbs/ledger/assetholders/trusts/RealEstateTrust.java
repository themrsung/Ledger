package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;

import java.util.UUID;

public final class RealEstateTrust extends Assetholder {
    public RealEstateTrust(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public RealEstateTrust(Assetholder copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.REAL_ESTATE_TRUST;
    }
}
