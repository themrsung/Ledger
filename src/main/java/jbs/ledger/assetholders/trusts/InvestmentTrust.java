package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.io.types.assetholders.trusts.InvestmentTrustData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

public final class InvestmentTrust extends Trust {
    public InvestmentTrust(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public InvestmentTrust(InvestmentTrust copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.INVESTMENT_TRUST;
    }


    // IO

    @Override
    public InvestmentTrustData toData() {
        InvestmentTrustData data = new InvestmentTrustData(super.toData());

        return data;
    }

    public static InvestmentTrust getEmptyInstance(UUID uniqueId) {
        return new InvestmentTrust(uniqueId);
    }

    private InvestmentTrust(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(InvestmentTrustData data, LedgerState state) {
        super.load(data, state);
    }
}
