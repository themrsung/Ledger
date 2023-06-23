package jbs.ledger.events.transfers.futures;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Commodity;
import jbs.ledger.types.assets.StackableNote;

import javax.annotation.Nullable;

public class CommodityFuturesTransferredEvent extends AssetTransferredEvent<StackableNote<Commodity>> {
    public CommodityFuturesTransferredEvent(
            Economic sender,
            Economic recipient,
            StackableNote<Commodity> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
