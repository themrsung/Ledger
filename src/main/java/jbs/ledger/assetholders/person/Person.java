package jbs.ledger.assetholders.person;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.classes.invitation.DirectorOffer;
import jbs.ledger.classes.invitation.EmployeeOffer;
import jbs.ledger.classes.navigation.GpsEntry;
import jbs.ledger.interfaces.common.Teleportable;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.person.PersonData;
import jbs.ledger.io.types.navigation.GpsEntryData;
import jbs.ledger.state.LedgerState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Players
 */
public final class Person extends Assetholder implements NationMember, Teleportable {
    public Person(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.premium = false;
        this.gpsEntries = new ArrayList<>();
    }
    public Person(Person copy) {
        super(copy);

        this.premium = copy.premium;
        this.premiumExpiration = copy.premiumExpiration;
        this.gpsEntries = copy.gpsEntries;
    }

    // Type
    @Override
    public AssetholderType getType() {
        return AssetholderType.PERSON;
    }

    // Teleportation
    @Override
    public void teleport(Location address) {
        Player player = toPlayer();
        if (player == null) return;

        player.teleport(address);
    }

    // IO
    public static Person getEmptyInstance(UUID uniqueId) {
        return new Person(uniqueId);
    }
    private Person(UUID uniqueId) {
        super(uniqueId);

        this.premium = false;
        this.gpsEntries = new ArrayList<>();
    }

    @Override
    public PersonData toData() {
        PersonData data = new PersonData(super.toData());

        data.premium = premium;
        data.premiumExpiration = premiumExpiration;

        for (GpsEntry ge : gpsEntries) {
            data.gpsEntries.add(ge.toData());
        }

        return data;
    }

    public void load(PersonData data, LedgerState state) {
        super.load(data, state);

        this.premium = data.premium;
        this.premiumExpiration = data.premiumExpiration;

        gpsEntries.clear();

        for (GpsEntryData ged : data.gpsEntries) {
            this.gpsEntries.add(GpsEntry.fromData(ged));
        }
    }

    // Utils
    @Nullable
    public Player toPlayer() {
        return Bukkit.getPlayer(getUniqueId());
    }

    // Premium
    private boolean premium;
    @Nullable
    private Date premiumExpiration;

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Nullable
    public Date getPremiumExpiration() {
        return premiumExpiration;
    }

    public void setPremiumExpiration(@Nullable Date premiumExpiration) {
        this.premiumExpiration = premiumExpiration;
    }

    private final ArrayList<GpsEntry> gpsEntries;

    public ArrayList<GpsEntry> getGpsEntries() {
        return new ArrayList<>(gpsEntries);
    }

    @Nullable
    public GpsEntry getGpsEntry(String name) {
        for (GpsEntry ge : getGpsEntries()) {
            if (ge.getName().equalsIgnoreCase(name)) {
                return ge;
            }
        }

        for (GpsEntry ge : getGpsEntries()) {
            if (ge.getName().toLowerCase().contains(name.toLowerCase())) {
                return ge;
            }
        }

        return null;
    }

    public void addGpsEntry(GpsEntry entry) {
        gpsEntries.add(entry);
    }

    public boolean removeGpsEntry(GpsEntry entry) {
        return gpsEntries.remove(entry);
    }

    // Invitations
    public transient final ArrayList<EmployeeOffer> inboundEmployeeOffers = new ArrayList<>();
    public transient final ArrayList<DirectorOffer> inboundDirectorOffers = new ArrayList<>();



    // Protection

    @Override
    public long getProtectionRadius() {
        return 25;
    }

    @Override
    public boolean hasPropertyAccess(Person person) {
        return this.equals(person);
    }
}
