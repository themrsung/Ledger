package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.io.types.assetholders.trusts.RealEstateTrustData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

public final class RealEstateTrust extends Trust {
    public RealEstateTrust(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name, symbol);
    }

    public RealEstateTrust(RealEstateTrust copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.REAL_ESTATE_TRUST;
    }


    // IO

    @Override
    public RealEstateTrustData toData() {
        RealEstateTrustData data = new RealEstateTrustData(super.toData());

        return data;
    }

    public static RealEstateTrust getEmptyInstance(UUID uniqueId) {
        return new RealEstateTrust(uniqueId);
    }

    private RealEstateTrust(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(RealEstateTrustData data, LedgerState state) {
        super.load(data, state);
    }
}
