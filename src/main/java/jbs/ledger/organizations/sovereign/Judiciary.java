package jbs.ledger.organizations.sovereign;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.supremecourt.SupremeCourtBill;
import jbs.ledger.interfaces.organization.Electorate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public final class Judiciary extends AbstractOrganization<Person> implements Electorate<Person> {
    public Judiciary(UUID uniqueId) {
        super(uniqueId);

        this.openMeetings = new ArrayList<>();
    }

    public Judiciary(Judiciary copy) {
        super(copy);

        this.owner = copy.owner;

        this.openMeetings = copy.openMeetings;
    }

    public Judiciary() {
        super();

        this.openMeetings = new ArrayList<>();
    }

    @Nullable
    public transient Nation owner = null;

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 사법부";
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


    public static Judiciary fromData(OrganizationData data, LedgerState state) {
        Judiciary judiciary = new Judiciary(data.uniqueId);

        for (UUID id : data.members) {
            judiciary.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            judiciary.setRepresentative(state.getPerson(data.representative));
        }

        judiciary.openMeetings.clear();

        for (MeetingData md : data.openMeetings) {
            judiciary.openMeetings.add((SupremeCourtBill) AbstractMeeting.fromData(md, state));
        }


        return judiciary;
    }
}
