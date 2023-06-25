package jbs.ledger.events.transfers.options;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;

import javax.annotation.Nullable;

public final class StockOptionTransferredEvent extends AssetTransferredEvent<Option<Stock>> {
    public StockOptionTransferredEvent(
            Economic sender,
            Economic recipient,
            Option<Stock> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
