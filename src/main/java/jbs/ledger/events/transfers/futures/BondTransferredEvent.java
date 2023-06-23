package jbs.ledger.events.transfers.futures;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Cash;
import jbs.ledger.types.assets.StackableNote;

import javax.annotation.Nullable;

public class BondTransferredEvent extends AssetTransferredEvent<StackableNote<Cash>> {
    public BondTransferredEvent(
            Economic sender,
            Economic recipient,
            StackableNote<Cash> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
