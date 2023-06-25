package jbs.ledger.classes.navigation;

import jbs.ledger.io.types.navigation.Address;
import jbs.ledger.io.types.navigation.GpsEntryData;
import jbs.ledger.utils.TypeUtils;
import org.bukkit.Location;

public final class GpsEntry {
    public GpsEntry(Location address, String name) {
        this.address = address;
        this.name = name;
    }

    private final Location address;
    private String name;

    public Location getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GpsEntryData toData() {
        GpsEntryData data = new GpsEntryData();

        data.address = new Address(address);
        data.name = name;

        return data;
    }

    public static GpsEntry fromData(GpsEntryData data) {
        return new GpsEntry(
                TypeUtils.addressToLocation(data.address),
                data.name
        );
    }
}
