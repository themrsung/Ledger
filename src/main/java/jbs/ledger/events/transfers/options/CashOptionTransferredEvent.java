package jbs.ledger.events.transfers.options;

import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.Option;

import javax.annotation.Nullable;

public final class CashOptionTransferredEvent extends AssetTransferredEvent<Option<Cash>> {
    public CashOptionTransferredEvent(
            Economic sender,
            Economic recipient,
            Option<Cash> asset,
            @Nullable AssetTransferCause cause
    ) {
        super(sender, recipient, asset, cause);
    }
}
