package jbs.ledger.assetholders.corporations.legal;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.corporations.general.ConstructionCompany;
import jbs.ledger.io.types.assetholders.corporations.general.ConstructionCompanyData;
import jbs.ledger.io.types.assetholders.corporations.legal.LawFirmData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Law firms can represent clients in court.
 */
public final class LawFirm extends Corporation {
    public LawFirm(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public LawFirm(LawFirm copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.LAW_FIRM;
    }

    // IO
    public LawFirmData toData() {
        return new LawFirmData();
    }

    private LawFirm(UUID uniqueId) {
        super(uniqueId);
    }

    public static LawFirm getEmptyInstance(UUID uniqueId) {
        return new LawFirm(uniqueId);
    }

    public void load(LawFirmData data, LedgerState state) {
        super.load(data, state);
    }
}
