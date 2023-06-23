package jbs.ledger.events.transfers.basic;

import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Commodity;

import javax.annotation.Nullable;

public class CommodityTransferredEvent extends AssetTransferredEvent<Commodity> {
    public CommodityTransferredEvent(
            Economic sender,
            Economic recipient,
            Commodity asset,
            @Nullable String reason
    ) {
        super(sender, recipient, asset, reason);
    }
}