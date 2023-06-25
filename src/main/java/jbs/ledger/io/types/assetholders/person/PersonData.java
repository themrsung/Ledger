package jbs.ledger.io.types.assetholders.person;

import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.navigation.GpsEntryData;

import java.util.ArrayList;
import java.util.Date;

public final class PersonData extends AssetholderData {
    public PersonData(AssetholderData parent) {
        super(parent);
    }
    public PersonData() {
        super();
    }

    public boolean premium = false;
    public Date premiumExpiration = null;
    public ArrayList<GpsEntryData> gpsEntries = new ArrayList<>();
}
