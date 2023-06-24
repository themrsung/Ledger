package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;

import java.util.UUID;

public final class InvestmentTrust extends Assetholder {
    public InvestmentTrust(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public InvestmentTrust(Assetholder copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.INVESTMENT_TRUST;
    }
}
