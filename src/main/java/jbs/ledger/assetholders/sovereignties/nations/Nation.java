package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.SovereignMember;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Nation extends Assetholder implements Sovereign, Organization<SovereignMember> {
    public Nation(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.members = new ArrayList<>();
    }

    public Nation(Nation copy) {
        super(copy);

        this.members = copy.members;
    }

    private final ArrayList<SovereignMember> members;

    public ArrayList<Person> getVotableMembers() {
        ArrayList<Person> people = new ArrayList<>();

        for (SovereignMember sm : getMembers()) {
            if (sm instanceof Person) {
                people.add((Person) sm);
            }
        }

        return people;
    }

    @Override
    public ArrayList<SovereignMember> getMembers() {
        return null;
    }

    @Override
    public void addMember(SovereignMember member) {

    }

    @Override
    public boolean removeMember(SovereignMember member) {
        return false;
    }

    @Nullable
    @Override
    public SovereignMember getRepresentative() {
        return null;
    }

    @Override
    public void setRepresentative(@Nullable SovereignMember representative) {

    }
}
