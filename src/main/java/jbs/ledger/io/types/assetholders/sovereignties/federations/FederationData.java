package jbs.ledger.io.types.assetholders.sovereignties.federations;

import jbs.ledger.io.types.assetholders.AssetholderData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class FederationData extends AssetholderData {
    public FederationData(AssetholderData parent) {
        super(parent);
    }

    public FederationData() {
        super();
    }
    public String symbol;

    public ArrayList<UUID> members = new ArrayList<>();
    @Nullable
    public UUID capital = null;
    public ArrayList<String> laws = new ArrayList<>();
}
