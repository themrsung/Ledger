package jbs.ledger.assetholders.corporations.general;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.general.MerchantData;
import jbs.ledger.io.types.assetholders.corporations.general.PublisherData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Publishers have the ability to copy books via commands.
 */
public final class Publisher extends Corporation {
    public Publisher(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public Publisher(Publisher copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PUBLISHER;
    }

    // IO
    public PublisherData toData() {
        return new PublisherData();
    }

    private Publisher(UUID uniqueId) {
        super(uniqueId);
    }

    public static Publisher getEmptyInstance(UUID uniqueId) {
        return new Publisher(uniqueId);
    }

    public void load(PublisherData data, LedgerState state) {
        super.load(data, state);
    }
}
