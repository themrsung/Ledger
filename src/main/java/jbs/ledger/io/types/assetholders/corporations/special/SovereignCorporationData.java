package jbs.ledger.io.types.assetholders.corporations.special;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;

import java.util.ArrayList;
import java.util.UUID;

public final class SovereignCorporationData extends CorporationData {
    public SovereignCorporationData(CorporationData parent) {
        super(parent);
    }
    public SovereignCorporationData() {
        super();
    }

    public String issuedCurrency;
    public ArrayList<String> laws = new ArrayList<>();

    public ArrayList<UUID> bannedPlayers = new ArrayList<>();
    public ArrayList<UUID> mutedPlayers = new ArrayList<>();

}
