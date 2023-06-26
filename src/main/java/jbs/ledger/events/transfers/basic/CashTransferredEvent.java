package jbs.ledger.events.transfers.basic;

import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;

import javax.annotation.Nullable;

public final class CashTransferredEvent extends AssetTransferredEvent<Cash> {
    public CashTransferredEvent(
            Economic sender,
            Economic recipient,
            Cash asset,
            @Nullable AssetTransferCause cause
    ) {
        super(sender, recipient, asset, cause);
    }
}
