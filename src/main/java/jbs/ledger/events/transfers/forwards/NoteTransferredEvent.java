package jbs.ledger.events.transfers.forwards;

import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;

public final class NoteTransferredEvent extends AssetTransferredEvent<UniqueNote<Cash>> {
    public NoteTransferredEvent(
            Economic sender,
            Economic recipient,
            UniqueNote<Cash> asset,
            @Nullable AssetTransferCause cause
    ) {
        super(sender, recipient, asset, cause);
    }
}
