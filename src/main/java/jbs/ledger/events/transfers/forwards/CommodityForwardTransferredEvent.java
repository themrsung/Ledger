package jbs.ledger.events.transfers.forwards;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Commodity;
import jbs.ledger.types.assets.UniqueNote;

import javax.annotation.Nullable;

public class CommodityForwardTransferredEvent extends AssetTransferredEvent<UniqueNote<Commodity>> {
    public CommodityForwardTransferredEvent(
            Economic sender,
            Economic recipient,
            UniqueNote<Commodity> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
