package jbs.ledger.events.transfers.futures;

import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.assets.basic.Stock;

import javax.annotation.Nullable;

public final class StockFuturesTransferredEvent extends AssetTransferredEvent<StackableNote<Stock>> {
    public StockFuturesTransferredEvent(
            Economic sender,
            Economic recipient,
            StackableNote<Stock> asset,
            @Nullable AssetTransferCause cause
    ) {
        super(sender, recipient, asset, cause);
    }
}
