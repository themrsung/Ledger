package jbs.ledger.io.types.assetholders.trusts;

import jbs.ledger.io.types.assetholders.AssetholderData;

import javax.annotation.Nullable;
import java.util.UUID;

public class TrustData extends AssetholderData {
    public TrustData(TrustData copy) {
        super(copy);

        this.symbol = copy.symbol;

        this.grantor = copy.grantor;
        this.trustee = copy.trustee;
        this.beneficiary = copy.beneficiary;
    }

    public TrustData(AssetholderData parent) {
        super(parent);
    }

    public TrustData() {
        super();
    }

    public String symbol;

    public UUID grantor;
    @Nullable
    public UUID trustee;
    @Nullable
    public UUID beneficiary;


}
