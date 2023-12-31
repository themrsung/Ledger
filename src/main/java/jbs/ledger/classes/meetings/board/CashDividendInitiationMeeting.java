package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.shareholder.CashDividendApprovalMeeting;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class CashDividendInitiationMeeting extends BoardMeeting {
    public static CashDividendInitiationMeeting newMeeting(
            Corporate corporation,
            double dividendPerShare
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_현금배당_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person d : corporation.getBoard().getMembers()) {
            voters.add(new Director(d, 1));
            votes++;
        }

        return new CashDividendInitiationMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                dividendPerShare
        );
    }

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
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Corporate) {
            Corporate corp = (Corporate) organization;
            corp.addOpenMeeting(CashDividendApprovalMeeting.newMeeting(
                    corp,
                    state,
                    getDividendPerShare()
            ));

        }
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
            directors.add(Director.fromData(vmd, state));
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
