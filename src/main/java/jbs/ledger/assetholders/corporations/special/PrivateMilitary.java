package jbs.ledger.assetholders.corporations.special;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

public final class PrivateMilitary extends Corporation {
    public PrivateMilitary(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public PrivateMilitary(PrivateMilitary copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRIVATE_MILITARY;
    }
}
