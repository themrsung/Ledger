package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.shareholder.Shareholder;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class BondIssueMeeting extends BoardMeeting {
    public static BondIssueMeeting newMeeting(
            Corporate corporation,
            StackableNote<Cash> bond
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_채권발행_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person d : corporation.getBoard().getMembers()) {
            voters.add(new Director(d, 1));
            votes++;
        }

        return new BondIssueMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                bond
        );
    }

    private BondIssueMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            StackableNote<Cash> bondToIssue
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.bondToIssue = bondToIssue;
    }

    private final StackableNote<Cash> bondToIssue;

    public StackableNote<Cash> getBondToIssue() {
        return bondToIssue;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Corporation) {
            Corporation c = (Corporation) organization;

            c.getBonds().add(getBondToIssue());
        }
    }

    @Override
    public MeetingType getType() {
        return MeetingType.BOARD_BOND_ISSUE;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.bondToIssue = (BondData) bondToIssue.toData();

        return data;
    }

    public static BondIssueMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> directors = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            directors.add(Director.fromData(vmd, state));
        }

        return new BondIssueMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                directors,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                StackableNote.fromData(data.bondToIssue, state)
        );
    }
}
