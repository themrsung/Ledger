package jbs.ledger.assetholders.corporations.special;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.corporations.general.Manufacturer;
import jbs.ledger.interfaces.warfare.Faction;
import jbs.ledger.io.types.assetholders.corporations.general.ManufacturerData;
import jbs.ledger.io.types.assetholders.corporations.special.PrivateMilitaryData;
import jbs.ledger.state.LedgerState;
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

    // IO
    public PrivateMilitaryData toData() {
        return new PrivateMilitaryData(super.toData());
    }

    private PrivateMilitary(UUID uniqueId) {
        super(uniqueId);
    }

    public static PrivateMilitary getEmptyInstance(UUID uniqueId) {
        return new PrivateMilitary(uniqueId);
    }

    public void load(PrivateMilitaryData data, LedgerState state) {
        super.load(data, state);
    }
}
