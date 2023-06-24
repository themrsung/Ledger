package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.io.types.assetholders.trusts.TrustData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

public abstract class Trust extends Assetholder {
    public Trust(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Trust(Trust copy) {
        super(copy);
    }

    // IO

    @Override
    public TrustData toData() {
        TrustData data = new TrustData(super.toData());

        return data;
    }

    protected Trust(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(TrustData data, LedgerState state) {
        super.load(data, state);
    }
}
