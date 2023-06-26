package jbs.ledger.io.types.assetholders.sovereignties.nations;

import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.organizations.OrganizationData;

import java.util.ArrayList;
import java.util.Date;

public final class ParliamentaryRepublicData extends NationData {
    public ParliamentaryRepublicData(NationData parent) {
        super(parent);
    }

    public ParliamentaryRepublicData() {
        super();
    }

    public OrganizationData administration = new OrganizationData();
    public OrganizationData legislature = new OrganizationData();
    public OrganizationData judiciary = new OrganizationData();
    public ArrayList<MeetingData> openMeetings = new ArrayList<>();

    public Date lastGeneralElectionDate;
}
