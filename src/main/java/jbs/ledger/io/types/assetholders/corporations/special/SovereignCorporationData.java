package jbs.ledger.io.types.assetholders.corporations.special;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;

import java.util.ArrayList;

public final class SovereignCorporationData extends CorporationData {
    public SovereignCorporationData(CorporationData parent) {
        super(parent);
    }
    public SovereignCorporationData() {
        super();
    }

    public String issuedCurrency;
    public ArrayList<String> laws = new ArrayList<>();

}
