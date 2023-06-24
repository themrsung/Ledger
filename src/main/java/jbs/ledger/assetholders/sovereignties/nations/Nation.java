package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.sovereignties.nations.NationData;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Nation extends Assetholder implements Sovereign, Organization<NationMember> {
    public Nation(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.members = new ArrayList<>();
        this.representative = null;
    }

    public Nation(Nation copy) {
        super(copy);

        this.members = copy.members;
        this.representative = copy.representative;
    }

    private final ArrayList<NationMember> members;
    @Nullable
    private NationMember representative;

    public ArrayList<Person> getVotableMembers() {
        ArrayList<Person> people = new ArrayList<>();

        for (NationMember sm : getMembers()) {
            if (sm instanceof Person) {
                people.add((Person) sm);
            }
        }

        return people;
    }

    @Override
    public ArrayList<NationMember> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(NationMember member) {
        members.add(member);
    }

    @Override
    public boolean removeMember(NationMember member) {
        return members.remove(member);
    }

    @Nullable
    @Override
    public NationMember getRepresentative() {
        return representative;
    }

    @Override
    public void setRepresentative(@Nullable NationMember representative) {
        this.representative = representative;
    }

    // IO

    @Override
    public NationData toData() {
        NationData data = new NationData(super.toData());

        for (NationMember m : getMembers()) {
            if (m instanceof Person) {
                data.citizens.add(m.getUniqueId());
            } else if (m instanceof Corporation) {
                data.corporations.add(m.getUniqueId());
            } else if (m instanceof Foundation) {
                data.foundations.add(m.getUniqueId());
            }
        }

        if (getRepresentative() != null) data.representative = getRepresentative().getUniqueId();

        return data;
    }

    public Nation(UUID uniqueId) {
        super(uniqueId);

        this.members = new ArrayList<>();
        this.representative = null;
    }

    public void load(NationData data, LedgerState state) {
        super.load(data, state);

        for (UUID p : data.citizens) {
            members.add(state.getPerson(p));
        }

        for (UUID c : data.corporations) {
            members.add(state.getCorporate(c));
        }

        for (UUID f : data.foundations) {
            members.add(state.getFoundation(f));
        }

        representative = state.getNationMember(data.representative);
    }
}
