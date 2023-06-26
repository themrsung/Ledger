package jbs.ledger.io.types.meetings;

import jbs.ledger.io.types.assets.synthetic.stackable.BondData;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class MeetingData {
    public MeetingData() {}

    public UUID uniqueId;
    public String symbol;
    public Date date;
    public ArrayList<VotableMemberData> votableMembers = new ArrayList<>();
    public long totalCastableVotes;
    public long castVotes;
    public long castYesVotes;
    public MeetingType type;

    // Cash dividend
    public double dividendPerShare = 0d;

    // Stock meetings
    public long sharesPerShare = 0;
    public long shares = 0;

    // Member hire/fire
    public UUID member = null;

    // Change name
    public String newName = null;

    // Legislature
    public String newLaw = null;
    public int oldLawIndex = 0;

    // Board meetings
    public BondData bondToIssue = null;
}
