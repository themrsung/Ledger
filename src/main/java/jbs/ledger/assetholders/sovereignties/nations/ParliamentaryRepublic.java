package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.io.types.assetholders.sovereignties.nations.ParliamentaryRepublicData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

/**
 * Parliamentary Republics have a prime minister.
 */
public final class ParliamentaryRepublic extends Nation {
    public ParliamentaryRepublic(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public ParliamentaryRepublic(ParliamentaryRepublic copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PARLIAMENTARY_REPUBLIC;
    }

    // IO

    @Override
    public ParliamentaryRepublicData toData() {
        ParliamentaryRepublicData data = new ParliamentaryRepublicData(super.toData());

        return data;
    }

    public static ParliamentaryRepublic getEmptyInstance(UUID uniqueId) {
        return new ParliamentaryRepublic(uniqueId);
    }

    private ParliamentaryRepublic(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(ParliamentaryRepublicData data, LedgerState state) {
        super.load(data, state);
    }
}
