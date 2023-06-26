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

public final class StockRetireApprovalMeeting extends AbstractMeeting<Person> {
    private StockRetireApprovalMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            long sharesToRetire
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.sharesToRetire = sharesToRetire;
    }

    private final long sharesToRetire;

    public long getSharesToRetire() {
        return sharesToRetire;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_STOCK_RETIRE;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.shares = sharesToRetire;

        return data;
    }

    public static StockRetireApprovalMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new StockRetireApprovalMeeting(
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
