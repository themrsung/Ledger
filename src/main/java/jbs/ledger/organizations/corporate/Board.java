package jbs.ledger.organizations.corporate;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.board.BoardMeeting;
import jbs.ledger.interfaces.organization.Electorate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public final class Board extends AbstractOrganization<Person> implements Electorate<Person> {
    public Board(UUID uniqueId) {
        super(uniqueId);

        this.openMeetings = new ArrayList<>();
    }

    public Board(Board copy) {
        super(copy);

        this.owner = copy.owner;
        this.openMeetings = copy.openMeetings;
    }

    public Board() {
        super();

        this.openMeetings = new ArrayList<>();
    }

    @Nullable
    public transient Assetholder owner = null;

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
    public boolean removeOpenMeeting(Meeting<Person> meeting) {
        return openMeetings.remove(meeting);
    }

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 이사회";
    }


    public static Board fromData(OrganizationData data, LedgerState state) {
        Board board = new Board(data.uniqueId);

        for (UUID id : data.members) {
            board.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            board.setRepresentative(state.getPerson(data.representative));
        }

        return board;
    }
}
