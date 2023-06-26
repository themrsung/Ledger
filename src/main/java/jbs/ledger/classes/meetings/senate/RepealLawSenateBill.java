package jbs.ledger.classes.meetings.senate;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.parliament.MemberOfParliament;
import jbs.ledger.interfaces.sovereignty.Tripartite;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class RepealLawSenateBill extends SenateBill {
    public static RepealLawSenateBill newMeeting(
            Tripartite nation,
            int oldLawIndex
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_법률폐지안_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getLegislature().getMembers()) {
            voters.add(new Senator(s, 1));
            votes++;
        }

        return new RepealLawSenateBill(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                oldLawIndex
        );
    }
    private RepealLawSenateBill(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            int oldLawIndex
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.oldLawIndex = oldLawIndex;
    }

    private final int oldLawIndex;

    public int getOldLawIndex() {
        return oldLawIndex;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SENATE_REPEAL_LAW;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.oldLawIndex = oldLawIndex;

        return data;
    }

    public static RepealLawSenateBill fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new RepealLawSenateBill(
                data.uniqueId,
                data.symbol,
                data.date,
                membersOfParliament,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.oldLawIndex
        );

    }
}