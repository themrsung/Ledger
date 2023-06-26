package jbs.ledger.io.types.organizations;

import jbs.ledger.io.types.meetings.MeetingData;

import java.util.ArrayList;
import java.util.UUID;

public class OrganizationData {
    public OrganizationData(OrganizationData copy) {
        this.uniqueId = copy.uniqueId;
        this.members = copy.members;
        this.representative = copy.representative;
        this.openMeetings = copy.openMeetings;
    }

    public OrganizationData() {}

    public UUID uniqueId;
    public ArrayList<UUID> members = new ArrayList<>();
    public UUID representative;
    public ArrayList<MeetingData> openMeetings = new ArrayList<>();
}
