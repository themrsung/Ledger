package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Issued shares go to the corporation.
 */
public final class StockIssueApprovalMeeting extends AbstractMeeting<Person> {
    private StockIssueApprovalMeeting(
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
        return MeetingType.SHAREHOLDER_STOCK_ISSUE;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.shares = sharesToIssue;

        return data;
    }

    public static StockIssueApprovalMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new StockIssueApprovalMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.shares
        );
    }
}
