package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.shareholder.Shareholder;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Passing of this meeting triggers a StockIssueApprovalMeeting for shareholders
 * Issued shares go to corporation
 */
public final class StockIssueInitiationMeeting extends BoardMeeting {
    public static StockIssueInitiationMeeting newMeeting(
            Corporate corporation,
            long shares
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_신주발행_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person d : corporation.getBoard().getMembers()) {
            voters.add(new Director(d, 1));
            votes++;
        }

        return new StockIssueInitiationMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                shares
        );
    }

    private StockIssueInitiationMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            long sharesToIssue
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.sharesToIssue = sharesToIssue;
    }

    private final long sharesToIssue;

    public long getSharesToIssue() {
        return sharesToIssue;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.BOARD_STOCK_ISSUE;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.shares = sharesToIssue;

        return data;
    }

    public static StockIssueInitiationMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> directors = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            directors.add(Director.fromData(vmd, state));
        }

        return new StockIssueInitiationMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                directors,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.shares
        );
    }
}
