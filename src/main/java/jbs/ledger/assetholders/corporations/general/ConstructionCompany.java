package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.general.CompanyData;
import jbs.ledger.io.types.assetholders.corporations.general.ConstructionCompanyData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Construction companies can be contracted to build in protected areas.
 */
public final class ConstructionCompany extends Corporation {
    public ConstructionCompany(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public ConstructionCompany(ConstructionCompany copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.CONSTRUCTION_COMPANY;
    }

    // IO
    public ConstructionCompanyData toData() {
        return new ConstructionCompanyData();
    }

    private ConstructionCompany(UUID uniqueId) {
        super(uniqueId);
    }

    public static ConstructionCompany getEmptyInstance(UUID uniqueId) {
        return new ConstructionCompany(uniqueId);
    }

    public void load(ConstructionCompanyData data, LedgerState state) {
        super.load(data, state);
    }
}
