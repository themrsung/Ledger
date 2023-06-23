package jbs.ledger.events.transfers.basic;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Stock;

import javax.annotation.Nullable;

public final class StockTransferredEvent extends AssetTransferredEvent<Stock> {
    public StockTransferredEvent(
            Economic sender,
            Economic recipient,
            Stock asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
