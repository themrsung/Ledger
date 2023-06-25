package jbs.ledger.io.types.assetholders.sovereignties.nations;

import jbs.ledger.io.types.organizations.OrganizationData;

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
}
