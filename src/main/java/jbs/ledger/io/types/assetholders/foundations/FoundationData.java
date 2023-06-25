package jbs.ledger.io.types.assetholders.foundations;

import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.organizations.OrganizationData;

public final class FoundationData extends AssetholderData {
    public FoundationData(AssetholderData copy) {
        super(copy);
    }

    public FoundationData() {
        super();
    }

    public String symbol;
    public OrganizationData board = new OrganizationData();
}
