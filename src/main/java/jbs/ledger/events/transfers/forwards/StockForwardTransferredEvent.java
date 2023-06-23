package jbs.ledger.events.transfers.forwards;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;

public final class StockForwardTransferredEvent extends AssetTransferredEvent<UniqueNote<Stock>> {
    public StockForwardTransferredEvent(
            Economic sender,
            Economic recipient,
            UniqueNote<Stock> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
