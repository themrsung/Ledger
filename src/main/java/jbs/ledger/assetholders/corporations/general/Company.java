package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.assetholders.corporations.general.CompanyData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * A generic company that can be used for any purpose.
 * Recommended for holdings companies.
 */
public final class Company extends Corporation {
    public Company(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public Company(Company copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.COMPANY;
    }

    // IO
    public CompanyData toData() {
        return new CompanyData();
    }

    private Company(UUID uniqueId) {
        super(uniqueId);
    }

    public static Company getEmptyInstance(UUID uniqueId) {
        return new Company(uniqueId);
    }

    public void load(CompanyData data, LedgerState state) {
        super.load(data, state);
    }
}
