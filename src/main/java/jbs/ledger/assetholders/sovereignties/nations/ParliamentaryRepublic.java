package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.referendums.Referendum;
import jbs.ledger.interfaces.organization.Electorate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.interfaces.sovereignty.Tripartite;
import jbs.ledger.io.types.assetholders.sovereignties.nations.ParliamentaryRepublicData;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.organizations.sovereign.Administration;
import jbs.ledger.organizations.sovereign.Judiciary;
import jbs.ledger.organizations.sovereign.Legislature;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Parliamentary Republics have a prime minister.
 */
public final class ParliamentaryRepublic extends Nation implements Tripartite, Electorate<Person> {
    public ParliamentaryRepublic(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name, symbol);

        this.administration = new Administration();
        this.legislature = new Legislature();
        this.judiciary = new Judiciary();

        this.openMeetings = new ArrayList<>();

        this.lastParliamentResetDate = new Date();
    }

    public ParliamentaryRepublic(ParliamentaryRepublic copy) {
        super(copy);

        this.administration = copy.administration;
        this.legislature = copy.legislature;
        this.judiciary = copy.judiciary;

        this.openMeetings = copy.openMeetings;

        this.lastParliamentResetDate = copy.lastParliamentResetDate;
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PARLIAMENTARY_REPUBLIC;
    }

    @Override
    public long getProtectionRadius() {
        return 5000;
    }

    // Tripartite
    private Administration administration;
    private Legislature legislature;
    private Judiciary judiciary;

    @Override
    public Administration getAdministration() {
        return administration;
    }

    @Override
    public Legislature getLegislature() {
        return legislature;
    }

    @Override
    public Judiciary getJudiciary() {
        return judiciary;
    }


    @Override
    public boolean hasAdministrativePower(Person person) {
        return getAdministration().getMembers().contains(person);
    }

    @Override
    public boolean hasLegislativePower(Person person) {
        return getLegislature().getMembers().contains(person);
    }

    @Override
    public boolean hasJudicialPower(Person person) {
        return getJudiciary().getMembers().contains(person);
    }

    @Override
    public boolean hasClemency(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    @Nullable
    @Override
    public NationMember getRepresentative() {
        return getAdministration().getRepresentative();
    }

    // Democracy

    private final ArrayList<Meeting<Person>> openMeetings;

    @Override
    public ArrayList<Meeting<Person>> getOpenMeetings() {
        return new ArrayList<>(openMeetings);
    }

    @Override
    public void addOpenMeeting(Meeting<Person> meeting) {
        openMeetings.add(meeting);
    }

    @Override
    public boolean removeOpenMeeting(Meeting<?> meeting) {
        return openMeetings.remove(meeting);
    }

    // Elections
    private Date lastParliamentResetDate;

    public Date getLastParliamentResetDate() {
        return lastParliamentResetDate;
    }

    public void setLastParliamentResetDate(Date lastParliamentResetDate) {
        this.lastParliamentResetDate = lastParliamentResetDate;
    }

    // IO

    @Override
    public ParliamentaryRepublicData toData() {
        ParliamentaryRepublicData data = new ParliamentaryRepublicData(super.toData());

        data.administration = administration.toData();
        data.legislature = legislature.toData();
        data.judiciary = judiciary.toData();

        for (Meeting<Person> m : openMeetings) {
            data.openMeetings.add(((Referendum) m).toData());
        }

        data.lastGeneralElectionDate = lastParliamentResetDate;

        return data;
    }

    public static ParliamentaryRepublic getEmptyInstance(UUID uniqueId) {
        return new ParliamentaryRepublic(uniqueId);
    }

    private ParliamentaryRepublic(UUID uniqueId) {
        super(uniqueId);

        this.administration = new Administration();
        this.legislature = new Legislature();
        this.judiciary = new Judiciary();

        this.openMeetings = new ArrayList<>();

        this.lastParliamentResetDate = null;
    }

    public void load(ParliamentaryRepublicData data, LedgerState state) {
        super.load(data, state);

        this.administration = Administration.fromData(data.administration, state);
        this.legislature = Legislature.fromData(data.administration, state);
        this.judiciary = Judiciary.fromData(data.administration, state);

        this.openMeetings.clear();

        for (MeetingData md : data.openMeetings) {
            this.openMeetings.add((Referendum) AbstractMeeting.fromData(md, state));
        }

        this.lastParliamentResetDate = data.lastGeneralElectionDate;
    }
}
