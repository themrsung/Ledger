package jbs.ledger.assetholders.sovereignties.federations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.assetholders.sovereignties.federations.FederationData;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public final class Federation extends Assetholder implements Organization<Sovereign>, Symbolic {
    public Federation(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name);

        this.symbol = symbol;

        this.members = new ArrayList<>();
        this.capital = null;
    }

    public Federation(Federation copy) {
        super(copy);
        this.symbol = copy.symbol;

        this.members = copy.members;
        this.capital = copy.capital;
    }

    private final ArrayList<Sovereign> members;

    private String symbol;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getSearchTag() {
        return getSymbol();
    }

    @Nullable
    private Sovereign capital;

    @Override
    public ArrayList<Sovereign> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(Sovereign member) {
        members.add(member);
    }

    @Override
    public boolean removeMember(Sovereign member) {
        return members.remove(member);
    }

    @Nullable
    @Override
    public Sovereign getRepresentative() {
        return capital;
    }

    @Override
    public void setRepresentative(@Nullable Sovereign representative) {
        this.capital = representative;
    }
    @Override
    public AssetholderType getType() {
        return AssetholderType.FEDERATION;
    }

    @Override
    public boolean hasPropertyAccess(Person person) {
        for (Sovereign s : getMembers()) {
            if (s.hasPropertyAccess(person)) {
                return true;
            }
        }
        return false;
    }

    // IO

    @Override
    public FederationData toData() {
        FederationData data = new FederationData(super.toData());

        data.symbol = symbol;

        return data;
    }

    public static Federation getEmptyInstance(UUID uniqueId) {
        return new Federation(uniqueId);
    }

    private Federation(UUID uniqueId) {
        super(uniqueId);

        this.members = new ArrayList<>();
    }

    public void load(FederationData data, LedgerState state) {
        super.load(data, state);

        this.symbol = data.symbol;

        members.clear();

        for (UUID n : data.members) {
            members.add(state.getSovereign(n));
        }
    }
}
