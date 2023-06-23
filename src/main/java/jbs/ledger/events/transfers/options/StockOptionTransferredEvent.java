package jbs.ledger.events.transfers.options;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.ConditionalNote;
import jbs.ledger.types.assets.Stock;

import javax.annotation.Nullable;

public class StockOptionTransferredEvent extends AssetTransferredEvent<ConditionalNote<Stock>> {
    public StockOptionTransferredEvent(
            Economic sender,
            Economic recipient,
            ConditionalNote<Stock> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
