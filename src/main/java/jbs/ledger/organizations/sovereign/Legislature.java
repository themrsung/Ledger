package jbs.ledger.organizations.sovereign;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.LegislatureBill;
import jbs.ledger.interfaces.organization.Electorate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public final class Legislature extends AbstractOrganization<Person> implements Electorate<Person> {
    public Legislature(UUID uniqueId) {
        super(uniqueId);

        this.openMeetings = new ArrayList<>();
    }

    public Legislature(Legislature copy) {
        super(copy);

        this.owner = copy.owner;

        this.openMeetings = copy.openMeetings;
    }

    public Legislature() {
        super();

        this.openMeetings = new ArrayList<>();
    }

    @Nullable
    public transient Nation owner = null;

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 입법부";
    }

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


    public static Legislature fromData(OrganizationData data, LedgerState state) {
        Legislature legislature = new Legislature(data.uniqueId);

        for (UUID id : data.members) {
            legislature.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            legislature.setRepresentative(state.getPerson(data.representative));
        }

        legislature.openMeetings.clear();

        for (MeetingData md : data.openMeetings) {
            legislature.openMeetings.add((LegislatureBill) AbstractMeeting.fromData(md, state));
        }

        return legislature;
    }
}
