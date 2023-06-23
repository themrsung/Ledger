package jbs.ledger.events.transfers.options;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.ConditionalNote;

import javax.annotation.Nullable;

public class CommodityOptionTransferredEvent extends AssetTransferredEvent<ConditionalNote<Commodity>> {
    public CommodityOptionTransferredEvent(
            Economic sender,
            Economic recipient,
            ConditionalNote<Commodity> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
