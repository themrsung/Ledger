package jbs.ledger.io.types.assetholders.sovereignties.nations;

import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.organizations.OrganizationData;

import java.util.ArrayList;
import java.util.Date;

public final class PresidentialRepublicData extends NationData {
    public PresidentialRepublicData(NationData parent) {
        super(parent);
    }

    public PresidentialRepublicData() {
        super();
    }

    public OrganizationData administration = new OrganizationData();
    public OrganizationData legislature = new OrganizationData();
    public OrganizationData judiciary = new OrganizationData();
    public ArrayList<MeetingData> openMeetings = new ArrayList<>();

    public Date lastPresidentialElectionDate;
    public Date lastGeneralElectionDate;
}
