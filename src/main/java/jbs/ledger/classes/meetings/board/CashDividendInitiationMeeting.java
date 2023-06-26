package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.shareholder.Shareholder;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class CashDividendInitiationMeeting extends BoardMeeting {
    private CashDividendInitiationMeeting(
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
        return MeetingType.BOARD_CASH_DIVIDEND;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.dividendPerShare = dividendPerShare;

        return data;
    }

    public static CashDividendInitiationMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> directors = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            directors.add(Shareholder.fromData(vmd, state));
        }

        return new CashDividendInitiationMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                directors,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.dividendPerShare
        );
    }
}
