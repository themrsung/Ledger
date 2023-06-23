package jbs.ledger.events.transfers.options;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.ConditionalNote;

import javax.annotation.Nullable;

public class ForexOptionTransferredEvent extends AssetTransferredEvent<ConditionalNote<Cash>> {
    public ForexOptionTransferredEvent(
            Economic sender,
            Economic recipient,
            ConditionalNote<Cash> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
