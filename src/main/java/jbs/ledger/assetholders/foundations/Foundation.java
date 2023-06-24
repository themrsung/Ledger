package jbs.ledger.assetholders.foundations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;

import java.util.UUID;

/**
 * Foundations are not taxed, and cannot pay dividends.
 * Unlike a corporation, there are no shareholders or shareholder meetings.
 * Decisions of consequence must pass a board meeting.
 */
public final class Foundation extends Assetholder {
    public Foundation(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Foundation(Foundation copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.FOUNDATION;
    }
}
