package jbs.ledger.assetholders.corporations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.shareholder.ShareholderMeeting;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.organizations.corporate.Board;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Corporation extends Assetholder implements Corporate  {
    public Corporation(UUID uniqueId, String name, String symbol, String currency, Cash capital, long shareCount) {
        super(uniqueId, name);

        this.symbol = symbol;
        this.preferredCurrency = currency;
        this.board = new Board();
        this.board.owner = this;
        this.capital = capital;
        this.shareCount = shareCount;
        this.members = new ArrayList<>();

        this.openMeetings = new ArrayList<>();
    }

    public Corporation(Corporation copy) {
        super(copy);

        this.symbol = copy.symbol;
        this.preferredCurrency = copy.preferredCurrency;
        this.board = copy.board;
        this.capital = copy.capital;
        this.shareCount = copy.shareCount;
        this.members = copy.members;

        this.openMeetings = copy.openMeetings;
    }

    private String symbol;
    private String preferredCurrency;
    private Board board;
    private Cash capital;
    private long shareCount;
    private final ArrayList<Person> members;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getSearchTag() {
        return getSymbol();
    }

    @Override
    public String getPreferredCurrency() {
        return preferredCurrency;
    }

    @Override
    public long getShareCount() {
        return shareCount;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Cash getCapital() {
        return capital;
    }

    @Override
    public void setCapital(Cash capital) {
        this.capital = capital;
    }

    @Override
    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
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
        return getBoard().getRepresentative();
    }

    @Override
    public void setRepresentative(@Nullable Person representative) {
        this.getBoard().setRepresentative(representative);
    }

    // Protection

    @Override
    public boolean hasPropertyAccess(Person person) {
        return getMembers().contains(person) || getBoard().getMembers().contains(person);
    }

    // Meetings

    private final ArrayList<Meeting<Assetholder>> openMeetings;

    @Override
    public ArrayList<Meeting<Assetholder>> getOpenMeetings() {
        return new ArrayList<>(openMeetings);
    }

    @Override
    public void addOpenMeeting(Meeting<Assetholder> meeting) {
        openMeetings.add(meeting);
    }

    @Override
    public boolean removeOpenMeeting(Meeting<?> meeting) {
        return openMeetings.remove(meeting);
    }

    // IO
    protected Corporation(UUID uniqueId) {
        super(uniqueId);

        this.symbol = null;
        this.preferredCurrency = null;
        this.capital = new Cash();
        this.board = new Board();
        this.members = new ArrayList<>();

        this.board.owner = this;

        this.openMeetings = new ArrayList<>();
    }

    @Override
    public CorporationData toData() {
        CorporationData data = new CorporationData(super.toData());

        data.symbol = symbol;
        data.preferredCurrency = preferredCurrency;
        data.board = board.toData();
        data.capital = capital.toData();
        data.shareCount = shareCount;

        for (Person m : members) {
            data.members.add(m.getUniqueId());
        }

        for (Meeting<Assetholder> m : openMeetings) {
            data.openMeetings.add(((ShareholderMeeting) m).toData());
        }

        return data;
    }

    public void load(CorporationData data, LedgerState state) {
        super.load(data, state);

        this.symbol = data.symbol;
        this.preferredCurrency = data.preferredCurrency;
        this.board = Board.fromData(data.board, state);
        this.board.owner = this;

        this.capital = Cash.fromData(data.capital);
        this.shareCount = data.shareCount;

        this.members.clear();

        for (UUID m : data.members) {
            this.members.add(state.getPerson(m));
        }

        this.openMeetings.clear();

        for (MeetingData md : data.openMeetings) {
            this.openMeetings.add((Meeting<Assetholder>) ShareholderMeeting.fromData(md, state));
        }
    }
}
