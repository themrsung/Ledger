package jbs.ledger.assetholders.corporations.special;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.interfaces.warfare.Faction;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Private militaries can join wars as an independent faction.
 */
public final class PrivateMilitary extends Corporation implements Faction {
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
