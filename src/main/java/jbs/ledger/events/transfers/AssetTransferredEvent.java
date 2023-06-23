package jbs.ledger.events.transfers;

import jbs.ledger.events.LedgerEvent;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;

/**
 * Ledger uses events to handle asset transfers.
 * All transactions require a valid sender and recipient.
 * Do not use this event directly. There is a child class for every asset variant.
 * @param <A> Type of asset to transfer
 */
public abstract class AssetTransferredEvent<A extends Asset> extends LedgerEvent {
    public AssetTransferredEvent(
            Economic sender,
            Economic recipient,
            A asset,
            @Nullable String reason
    ) {
        this.sender = sender;
        this.recipient = recipient;
        this.asset = asset;
        this.reason = reason;
    }

    private final Economic sender;
    private final Economic recipient;
    private final A asset;
    @Nullable
    private final String reason;

    public Economic getSender() {
        return sender;
    }

    public Economic getRecipient() {
        return recipient;
    }

    public A getAsset() {
        return asset;
    }

    @Nullable
    public String getReason() {
        return reason;
    }
}
