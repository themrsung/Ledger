package jbs.ledger.assetholders.corporations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.organizations.Board;
import jbs.ledger.types.assets.basic.Cash;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Corporation extends Assetholder implements Corporate {
    public Corporation(UUID uniqueId, String name, String symbol, String currency, Cash capital) {
        super(uniqueId, name);

        this.symbol = symbol;
        this.preferredCurrency = currency;
        this.board = new Board();
        this.capital = capital;
        this.members = new ArrayList<>();
        this.representative = null;
    }

    public Corporation(Corporation copy) {
        super(copy);

        this.symbol = copy.symbol;
        this.preferredCurrency = copy.preferredCurrency;
        this.board = copy.board;
        this.capital = copy.capital;
        this.members = copy.members;
        this.representative = copy.representative;
    }

    private final String symbol;
    private final String preferredCurrency;
    private final Organization<Person> board;
    private final Cash capital;
    private final ArrayList<Person> members;
    @Nullable
    private Person representative;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getPreferredCurrency() {
        return preferredCurrency;
    }

    @Override
    public Organization<Person> getBoard() {
        return board;
    }

    @Override
    public Cash getCapital() {
        return capital;
    }

    @Override
    public ArrayList<Person> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(Person member) {
        members.add(member);
    }

    @Override
    public boolean removeMember(Person member) {
        return members.remove(member);
    }

    @Override
    @Nullable
    public Person getRepresentative() {
        return representative;
    }

    @Override
    public void setRepresentative(@Nullable Person representative) {
        this.representative = representative;
    }
}
