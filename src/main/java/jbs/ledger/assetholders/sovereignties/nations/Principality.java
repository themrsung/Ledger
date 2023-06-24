package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PresidentialRepublicData;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PrincipalityData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

/**
 * Principalities have a monarch.
 */
public final class Principality extends Nation {
    public Principality(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name, symbol);
    }

    public Principality(Principality copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRINCIPALITY;
    }

    // IO

    @Override
    public PrincipalityData toData() {
        PrincipalityData data = new PrincipalityData(super.toData());

        return data;
    }

    public static Principality getEmptyInstance(UUID uniqueId) {
        return new Principality(uniqueId);
    }

    private Principality(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(PrincipalityData data, LedgerState state) {
        super.load(data, state);
    }
}
