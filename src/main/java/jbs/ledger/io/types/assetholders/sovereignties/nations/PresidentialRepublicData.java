package jbs.ledger.io.types.assetholders.sovereignties.nations;

import jbs.ledger.io.types.organizations.OrganizationData;

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
}
