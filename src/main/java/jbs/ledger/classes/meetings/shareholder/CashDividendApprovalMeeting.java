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

public final class CashDividendApprovalMeeting extends AbstractMeeting<Person> {
    private CashDividendApprovalMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            double dividendPerShare
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.dividendPerShare = dividendPerShare;
    }

    private final double dividendPerShare;

    public double getDividendPerShare() {
        return dividendPerShare;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_CASH_DIVIDEND;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.dividendPerShare = dividendPerShare;

        return data;
    }

    public static CashDividendApprovalMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new CashDividendApprovalMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.dividendPerShare
        );
    }
}
