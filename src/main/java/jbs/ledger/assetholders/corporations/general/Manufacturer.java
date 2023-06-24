package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.general.ManufacturerData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Manufacturers have the ability to craft credit card terminals (Command blocks)
 */
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

    @Override
    public long getProtectionRadius() {
        return 125;
    }

    // IO
    public ManufacturerData toData() {
        return new ManufacturerData(super.toData());
    }

    private Manufacturer(UUID uniqueId) {
        super(uniqueId);
    }

    public static Manufacturer getEmptyInstance(UUID uniqueId) {
        return new Manufacturer(uniqueId);
    }

    public void load(ManufacturerData data, LedgerState state) {
        super.load(data, state);
    }
}
