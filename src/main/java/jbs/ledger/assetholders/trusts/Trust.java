package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.organization.Trusteeship;
import jbs.ledger.io.types.assetholders.trusts.TrustData;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class Trust extends Assetholder implements Symbolic, Trusteeship {
    public Trust(UUID uniqueId, String name, String symbol, Assetholder grantor) {
        super(uniqueId, name);
        this.symbol = symbol;

        this.grantor = grantor;
        this.trustee = null;
        this.beneficiary = null;
    }

    public Trust(Trust copy) {
        super(copy);
        this.symbol = copy.symbol;

        this.grantor = copy.grantor;
        this.trustee = copy.trustee;
        this.beneficiary = copy.beneficiary;
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

    // Members

    private Assetholder grantor;
    @Nullable
    private Assetholder trustee;
    @Nullable
    private Assetholder beneficiary;

    @Override
    public Assetholder getGrantor() {
        return grantor;
    }

    @Override
    @Nullable
    public Assetholder getTrustee() {
        return trustee;
    }
    @Override
    @Nullable
    public Assetholder getBeneficiary() {
        return beneficiary;
    }

    @Override
    public void setTrustee(@Nullable Assetholder trustee) {
        this.trustee = trustee;
    }

    @Override
    public void setBeneficiary(@Nullable Assetholder beneficiary) {
        this.beneficiary = beneficiary;
    }

    // Protection

    @Override
    public boolean hasPropertyAccess(Person person) {
        if (getTrustee() == null) return false;

        return getTrustee().equals(person) || getTrustee().hasPropertyAccess(person);
    }

    // IO

    @Override
    public TrustData toData() {
        TrustData data = new TrustData(super.toData());

        return data;
    }

    protected Trust(UUID uniqueId) {
        super(uniqueId);

        this.grantor = null;
        this.trustee = null;
        this.beneficiary = null;
    }

    public void load(TrustData data, LedgerState state) {
        super.load(data, state);
        this.symbol = data.symbol;;

        this.grantor = state.getAssetholder(data.grantor);
        this.trustee = state.getAssetholder(data.trustee);
        this.beneficiary = state.getAssetholder(data.beneficiary);
    }
}
