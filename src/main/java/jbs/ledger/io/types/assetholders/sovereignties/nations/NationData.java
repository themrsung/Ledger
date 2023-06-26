package jbs.ledger.io.types.assetholders.sovereignties.nations;

import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.meetings.MeetingData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class NationData extends AssetholderData {
    public NationData(AssetholderData parent) {
        super(parent);
    }

    public NationData(NationData copy) {
        super(copy);

        this.symbol = copy.symbol;
        this.citizens = copy.citizens;
        this.corporations = copy.corporations;
        this.foundations = copy.foundations;
        this.representative = copy.representative;
        this.issuedCurrency = copy.issuedCurrency;
        this.laws = copy.laws;
    }

    public NationData() {
        super();
    }

    public String symbol;

    public ArrayList<UUID> citizens = new ArrayList<>();
    public ArrayList<UUID> corporations = new ArrayList<>();
    public ArrayList<UUID> foundations = new ArrayList<>();
    public UUID representative = null;
    @Nullable
    public String issuedCurrency = null;
    public ArrayList<String> laws = new ArrayList<>();
}
