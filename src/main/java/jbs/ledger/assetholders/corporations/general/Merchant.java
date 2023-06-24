package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.general.ManufacturerData;
import jbs.ledger.io.types.assetholders.corporations.general.MerchantData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Merchants have the ability to accept card payments.
 */
public final class Merchant extends Corporation {
    public Merchant(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public Merchant(Merchant copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.MERCHANT;
    }

    // IO
    public MerchantData toData() {
        return new MerchantData(super.toData());
    }

    private Merchant(UUID uniqueId) {
        super(uniqueId);
    }

    public static Merchant getEmptyInstance(UUID uniqueId) {
        return new Merchant(uniqueId);
    }

    public void load(MerchantData data, LedgerState state) {
        super.load(data, state);
    }
}
