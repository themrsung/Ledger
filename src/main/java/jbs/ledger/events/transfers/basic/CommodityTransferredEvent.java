package jbs.ledger.events.transfers.basic;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Commodity;

import javax.annotation.Nullable;

public final class CommodityTransferredEvent extends AssetTransferredEvent<Commodity> {
    public CommodityTransferredEvent(
            Economic sender,
            Economic recipient,
            Commodity asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}
