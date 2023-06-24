package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PresidentialRepublicData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

/**
 * Presidential Republics have a president and vice president.
 */
public final class PresidentialRepublic extends Nation {
    public PresidentialRepublic(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public PresidentialRepublic(PresidentialRepublic copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRESIDENTIAL_REPUBLIC;
    }

    // IO

    @Override
    public PresidentialRepublicData toData() {
        PresidentialRepublicData data = new PresidentialRepublicData(super.toData());

        return data;
    }

    public static PresidentialRepublic getEmptyInstance(UUID uniqueId) {
        return new PresidentialRepublic(uniqueId);
    }

    private PresidentialRepublic(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(PresidentialRepublicData data, LedgerState state) {
        super.load(data, state);
    }
}
