package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.address.Headquartered;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.currency.CurrencyIssuer;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.sovereignties.nations.NationData;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.state.LedgerState;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Nation extends Assetholder implements Sovereign, Organization<NationMember>, Symbolic, CurrencyIssuer {
    public Nation(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name);

        this.symbol = symbol;
        this.members = new ArrayList<>();
        this.representative = null;

        this.issuedCurrency = null;

        this.laws = new ArrayList<>();

        this.bannedPlayers = new ArrayList<>();
        this.mutedPlayers = new ArrayList<>();
    }

    public Nation(Nation copy) {
        super(copy);

        this.symbol = copy.symbol;
        this.members = copy.members;
        this.representative = copy.representative;

        this.issuedCurrency = copy.issuedCurrency;

        this.laws = copy.laws;

        this.bannedPlayers = copy.bannedPlayers;
        this.mutedPlayers = copy.mutedPlayers;
    }

    private String symbol;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getSearchTag() {
        return getSymbol();
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

    public ArrayList<Person> getCitizens() {
        ArrayList<Person> citizens = new ArrayList<>();

        for (NationMember m : getMembers()) {
            if (m instanceof Person) {
                citizens.add((Person) m);
            }
        }

        return citizens;
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

    @Override
    public long getProtectionRadius() {
        return 500;
    }

    @Override
    public boolean hasPropertyAccess(Person person) {
        for (NationMember nm : getMembers()) {
            if (nm instanceof Headquartered) {
                Headquartered hq = (Headquartered) nm;
                if (hq.hasPropertyAccess(person)) return true;
            }
        }

        return getMembers().contains(person);
    }

    // Currency
    @Nullable
    private String issuedCurrency;

    @Override
    @Nullable
    public String getIssuedCurrency() {
        return issuedCurrency;
    }

    public void setIssuedCurrency(@Nullable String issuedCurrency) {
        this.issuedCurrency = issuedCurrency;
    }

    // Laws
    private ArrayList<String> laws;

    @Override
    public ArrayList<String> getLaws() {
        return new ArrayList<>(laws);
    }

    @Override
    public void addLaw(String law) {

    }

    @Override
    public boolean removeLaw(String law) {
        return laws.remove(law);
    }

    @Override
    public void removeLaw(int index) {
        if (index >= getLaws().size()) return;
        laws.remove(index);
    }

    @Override
    public void changeLaw(int index, String law) {
        if (index >= laws.size()) return;

        laws.set(index, law);
    }

    // Punishment

    private final ArrayList<Person> bannedPlayers;
    private final ArrayList<Person> mutedPlayers;

    @Override
    public ArrayList<Person> getBannedPlayers() {
        return new ArrayList<>(bannedPlayers);
    }

    @Override
    public void addBannedPlayer(Person p) {
        bannedPlayers.add(p);
    }

    @Override
    public boolean removeBannedPlayer(Person p) {
        return bannedPlayers.remove(p);
    }

    @Override
    public ArrayList<Person> getMutedPlayers() {
        return new ArrayList<>(mutedPlayers);
    }

    @Override
    public void addMutedPlayer(Person p) {
        mutedPlayers.add(p);
    }

    @Override
    public boolean removeMutedPlayer(Person p) {
        return mutedPlayers.remove(p);
    }

    // IO
    @Override
    public NationData toData() {
        NationData data = new NationData(super.toData());

        data.symbol = symbol;

        for (NationMember m : getMembers()) {
            if (m instanceof Person) {
                data.citizens.add(m.getUniqueId());
            } else if (m instanceof Corporation) {
                data.corporations.add(m.getUniqueId());
            } else if (m instanceof Foundation) {
                data.foundations.add(m.getUniqueId());
            }
        }

        data.issuedCurrency = issuedCurrency;

        if (getRepresentative() != null) data.representative = getRepresentative().getUniqueId();

        for (Person p : getBannedPlayers()) {
            data.bannedPlayers.add(p.getUniqueId());
        }

        for (Person p : getMutedPlayers()) {
            data.mutedPlayers.add(p.getUniqueId());
        }

        data.laws = laws;

        return data;
    }

    public Nation(UUID uniqueId) {
        super(uniqueId);

        this.symbol = null;
        this.members = new ArrayList<>();
        this.representative = null;
        this.issuedCurrency = null;

        this.laws = new ArrayList<>();

        this.bannedPlayers = new ArrayList<>();
        this.mutedPlayers = new ArrayList<>();
    }

    public void load(NationData data, LedgerState state) {
        super.load(data, state);

        this.symbol = data.symbol;

        this.members.clear();

        for (UUID p : data.citizens) {
            members.add(state.getPerson(p));
        }

        for (UUID c : data.corporations) {
            members.add(state.getCorporate(c));
        }

        for (UUID f : data.foundations) {
            members.add(state.getFoundation(f));
        }

        this.issuedCurrency = data.issuedCurrency;

        this.laws = data.laws;

        this.bannedPlayers.clear();
        for (UUID id : data.bannedPlayers) {
            this.bannedPlayers.add(state.getPerson(id));
        }

        this.mutedPlayers.clear();
        for (UUID id : data.mutedPlayers) {
            this.mutedPlayers.add(state.getPerson(id));
        }

        representative = state.getNationMember(data.representative);
    }
}
