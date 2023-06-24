package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

public final class Manufacturer extends Corporation {
    public Manufacturer(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public Manufacturer(Manufacturer copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.MANUFACTURER;
    }
}
