package jbs.ledger.io.types.assetholders.trusts;

import jbs.ledger.io.types.assetholders.AssetholderData;

public class TrustData extends AssetholderData {
    public TrustData(TrustData copy) {
        super(copy);

        this.symbol = copy.symbol;
    }

    public TrustData(AssetholderData parent) {
        super(parent);
    }

    public TrustData() {
        super();
    }

    public String symbol;


}
