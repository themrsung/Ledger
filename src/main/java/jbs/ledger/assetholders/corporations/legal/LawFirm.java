package jbs.ledger.assetholders.corporations.legal;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
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
}
