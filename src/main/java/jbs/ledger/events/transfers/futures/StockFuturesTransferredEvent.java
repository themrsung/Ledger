package jbs.ledger.events.transfers.futures;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.StackableNote;
import jbs.ledger.types.assets.Stock;

import javax.annotation.Nullable;

public class StockFuturesTransferredEvent extends AssetTransferredEvent<StackableNote<Stock>> {
    public StockFuturesTransferredEvent(
            Economic sender,
            Economic recipient,
            StackableNote<Stock> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
