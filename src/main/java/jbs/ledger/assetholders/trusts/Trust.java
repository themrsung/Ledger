package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.io.types.assetholders.trusts.TrustData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

public abstract class Trust extends Assetholder implements Symbolic {
    public Trust(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name);
        this.symbol = symbol;
    }

    public Trust(Trust copy) {
        super(copy);
        this.symbol = copy.symbol;
    }

    public String symbol;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getSearchTag() {
        return getSymbol();
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
        this.symbol = data.symbol;;
    }
}
