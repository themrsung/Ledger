package jbs.ledger.assetholders.foundations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.foundations.FoundationData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

/**
 * Foundations are not taxed, and cannot pay dividends.
 * Unlike a corporation, there are no shareholders or shareholder meetings.
 * Decisions of consequence must pass a board meeting.
 */
public final class Foundation extends Assetholder implements NationMember {
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

    // IO
    public FoundationData toData() {
        return new FoundationData();
    }

    private Foundation(UUID uniqueId) {
        super(uniqueId);
    }

    public static Foundation getEmptyInstance(UUID uniqueId) {
        return new Foundation(uniqueId);
    }

    public void load(FoundationData data, LedgerState state) {
        super.load(data, state);
    }
}
