package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.general.DistilleryData;
import jbs.ledger.io.types.assetholders.corporations.general.MerchantData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Distilleries can copy potions.
 */
public final class Distillery extends Corporation {
    public Distillery(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital,
            long shareCount
    ) {
        super(uniqueId, name, symbol, currency, capital, shareCount);
    }

    public Distillery(Distillery copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.DISTILLERY;
    }

    @Override
    public long getProtectionRadius() {
        return 100;
    }

    // IO
    public DistilleryData toData() {
        return new DistilleryData(super.toData());
    }

    private Distillery(UUID uniqueId) {
        super(uniqueId);
    }

    public static Distillery getEmptyInstance(UUID uniqueId) {
        return new Distillery(uniqueId);
    }

    public void load(DistilleryData data, LedgerState state) {
        super.load(data, state);
    }
}
