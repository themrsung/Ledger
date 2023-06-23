package jbs.ledger.events.transfers.forwards;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;

public final class CommodityForwardTransferredEvent extends AssetTransferredEvent<UniqueNote<Commodity>> {
    public CommodityForwardTransferredEvent(
            Economic sender,
            Economic recipient,
            UniqueNote<Commodity> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
