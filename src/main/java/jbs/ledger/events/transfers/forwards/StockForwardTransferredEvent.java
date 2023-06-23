package jbs.ledger.events.transfers.forwards;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Stock;
import jbs.ledger.types.assets.UniqueNote;

import javax.annotation.Nullable;

public class StockForwardTransferredEvent extends AssetTransferredEvent<UniqueNote<Stock>> {
    public StockForwardTransferredEvent(
            Economic sender,
            Economic recipient,
            UniqueNote<Stock> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}