package jbs.ledger.io.types.assetholders.sovereignties.nations;

import jbs.ledger.io.types.assetholders.AssetholderData;

import java.util.ArrayList;
import java.util.UUID;

public class NationData extends AssetholderData {
    public NationData(AssetholderData parent) {
        super(parent);
    }

    public NationData(NationData copy) {
        super(copy);

        this.citizens = copy.citizens;
        this.corporations = copy.corporations;
        this.foundations = copy.foundations;
        this.representative = copy.representative;
    }

    public NationData() {
        super();
    }

    public ArrayList<UUID> citizens = new ArrayList<>();
    public ArrayList<UUID> corporations = new ArrayList<>();
    public ArrayList<UUID> foundations = new ArrayList<>();
    public UUID representative = null;
}
