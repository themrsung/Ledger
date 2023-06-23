package jbs.ledger.events.transfers.basic;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;

import javax.annotation.Nullable;

public final class BondTransferredEvent extends AssetTransferredEvent<StackableNote<Cash>> {
    public BondTransferredEvent(
            Economic sender,
            Economic recipient,
            StackableNote<Cash> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
