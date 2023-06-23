package jbs.ledger.events.transfers.forwards;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Cash;
import jbs.ledger.types.assets.UniqueNote;

import javax.annotation.Nullable;

public class NoteTransferredEvent extends AssetTransferredEvent<UniqueNote<Cash>> {
    public NoteTransferredEvent(
            Economic sender,
            Economic recipient,
            UniqueNote<Cash> asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
