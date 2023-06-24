package jbs.ledger.io.types.organizations;

import java.util.ArrayList;
import java.util.UUID;

public class OrganizationData {
    public OrganizationData(OrganizationData copy) {
        this.uniqueId = copy.uniqueId;
        this.members = copy.members;
        this.representative = copy.representative;
    }

    public OrganizationData() {}

    public UUID uniqueId;
    public ArrayList<UUID> members;
    public UUID representative;
}
