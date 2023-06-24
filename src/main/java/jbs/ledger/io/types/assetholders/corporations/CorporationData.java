package jbs.ledger.io.types.assetholders.corporations;

import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.organizations.OrganizationData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class CorporationData extends AssetholderData {
    public CorporationData(AssetholderData parent) {
        super(parent);
    }

    public CorporationData(CorporationData copy) {
        super(copy);
        this.symbol = copy.symbol;
        this.preferredCurrency = copy.preferredCurrency;
        this.board = copy.board;
        this.capital = copy.capital;
        this.members = copy.members;
        this.representative = copy.representative;
    }

    public CorporationData() {
        super();
    }

    public String symbol;
    public String preferredCurrency;
    public OrganizationData board;
    public CashData capital;
    public ArrayList<UUID> members = new ArrayList<>();
    @Nullable
    public UUID representative = null;
}
