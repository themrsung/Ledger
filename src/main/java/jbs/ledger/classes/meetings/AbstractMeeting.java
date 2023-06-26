package jbs.ledger.classes.meetings;

import jbs.ledger.classes.meetings.board.*;
import jbs.ledger.classes.meetings.federation.ChangeCapitalResolution;
import jbs.ledger.classes.meetings.federation.ChangeNameResolution;
import jbs.ledger.classes.meetings.federation.KickMemberResolution;
import jbs.ledger.classes.meetings.federation.NewMemberResolution;
import jbs.ledger.classes.meetings.parliament.ChangeLawParliamentBill;
import jbs.ledger.classes.meetings.parliament.MotionOfNoConfidence;
import jbs.ledger.classes.meetings.parliament.NewLawParliamentBill;
import jbs.ledger.classes.meetings.parliament.RepealLawParliamentBill;
import jbs.ledger.classes.meetings.referendums.ChangeNameReferendum;
import jbs.ledger.classes.meetings.senate.ChangeLawSenateBill;
import jbs.ledger.classes.meetings.senate.ImpeachPresidentSenateBill;
import jbs.ledger.classes.meetings.senate.NewLawSenateBill;
import jbs.ledger.classes.meetings.senate.RepealLawSenateBill;
import jbs.ledger.classes.meetings.shareholder.*;
import jbs.ledger.classes.meetings.supremecourt.ImpeachPresidentSupremeCourtBill;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractMeeting<M extends Unique> implements Meeting<M> {
    protected AbstractMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<M>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        this.uniqueId = uniqueId;
        this.symbol = symbol;
        this.date = date;
        this.votableMembers = votableMembers;
        this.totalCastableVotes = totalCastableVotes;
        this.castVotes = castVotes;
        this.castYesVotes = castYesVotes;
    }

    private final UUID uniqueId;
    private final String symbol;
    private final Date date;
    private final ArrayList<VotableMember<M>> votableMembers;
    private final long totalCastableVotes;
    private long castVotes;
    private long castYesVotes;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public ArrayList<VotableMember<M>> getVotableMembers() {
        return new ArrayList<>(votableMembers);
    }

    @Override
    public boolean vote(VotableMember<?> member, boolean yes) {
        if (!getVotableMembers().contains(member)) return false;

        long votes = member.getVotes();

        castVotes += votes;
        if (yes) castYesVotes += votes;

        this.votableMembers.remove(member);
        return true;
    }

    @Override
    public long getCastYesVotes() {
        return castYesVotes;
    }

    @Override
    public long getCastVotes() {
        return castVotes;
    }

    @Override
    public long getTotalCastableVotes() {
        return totalCastableVotes;
    }

    // IO
    public MeetingData toData() {
        MeetingData data = new MeetingData();

        data.uniqueId = uniqueId;
        data.symbol = symbol;
        data.date = date;

        for (VotableMember<M> member : votableMembers) {
            data.votableMembers.add(member.toData());
        }

        data.castVotes = castVotes;
        data.castYesVotes = castYesVotes;

        data.type = getType();

        return data;
    }

    public static AbstractMeeting<?> fromData(MeetingData data, LedgerState state) {
        switch (data.type) {
            case BOARD_CASH_DIVIDEND:
                return CashDividendInitiationMeeting.fromData(data, state);
            case BOARD_STOCK_DIVIDEND:
                return StockDividendInitiationMeeting.fromData(data, state);
            case BOARD_STOCK_SPLIT:
                return StockSplitInitiationMeeting.fromData(data, state);
            case BOARD_STOCK_RETIRE:
                return StockRetireInitiationMeeting.fromData(data, state);
            case BOARD_STOCK_ISSUE:
                return StockIssueInitiationMeeting.fromData(data, state);
            case BOARD_BOND_ISSUE:
                return BondIssueMeeting.fromData(data, state);

            case SHAREHOLDER_HIRE_CEO:
                return HireCeoMeeting.fromData(data, state);
            case SHAREHOLDER_FIRE_CEO:
                return FireCeoMeeting.fromData(data, state);
            case SHAREHOLDER_HIRE_DIRECTOR:
                return HireDirectorMeeting.fromData(data, state);
            case SHAREHOLDER_FIRE_DIRECTOR:
                return FireDirectorMeeting.fromData(data, state);
            case SHAREHOLDER_CHANGE_NAME:
                return ChangeNameMeeting.fromData(data, state);
            case SHAREHOLDER_CASH_DIVIDEND:
                return CashDividendApprovalMeeting.fromData(data, state);
            case SHAREHOLDER_STOCK_DIVIDEND:
                return StockDividendApprovalMeeting.fromData(data, state);
            case SHAREHOLDER_STOCK_ISSUE:
                return StockIssueApprovalMeeting.fromData(data, state);
            case SHAREHOLDER_STOCK_SPLIT:
                return StockSplitApprovalMeeting.fromData(data, state);
            case SHAREHOLDER_STOCK_RETIRE:
                return StockRetireApprovalMeeting.fromData(data, state);
            case SHAREHOLDER_LIQUIDATE:
                return LiquidationMeeting.fromData(data, state);

            case PARLIAMENT_NEW_LAW:
                return NewLawParliamentBill.fromData(data, state);
            case PARLIAMENT_CHANGE_LAW:
                return ChangeLawParliamentBill.fromData(data, state);
            case PARLIAMENT_REPEAL_LAW:
                return RepealLawParliamentBill.fromData(data, state);
            case PARLIAMENT_NO_CONFIDENCE:
                return MotionOfNoConfidence.fromData(data, state);

            case SENATE_NEW_LAW:
                return NewLawSenateBill.fromData(data, state);
            case SENATE_CHANGE_LAW:
                return ChangeLawSenateBill.fromData(data, state);
            case SENATE_REPEAL_LAW:
                return RepealLawSenateBill.fromData(data, state);
            case SENATE_IMPEACH_PRESIDENT:
                return ImpeachPresidentSenateBill.fromData(data, state);

            case SUPREME_COURT_IMPEACH_PRESIDENT:
                return ImpeachPresidentSupremeCourtBill.fromData(data, state);

            case REFERENDUM_CHANGE_NAME:
                return ChangeNameReferendum.fromData(data, state);

            case FEDERATION_CHANGE_NAME:
                return ChangeNameResolution.fromData(data, state);
            case FEDERATION_NEW_MEMBER:
                return NewMemberResolution.fromData(data, state);
            case FEDERATION_CHANGE_CAPITAL:
                return ChangeCapitalResolution.fromData(data, state);
            case FEDERATION_KICK_MEMBER:
                return KickMemberResolution.fromData(data, state);
        }

        return null;
    }
}
