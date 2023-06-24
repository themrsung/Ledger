package jbs.ledger.assetholders.person;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.interfaces.common.Teleportable;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.person.PersonData;
import jbs.ledger.state.LedgerState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

/**
 * Players
 */
public final class Person extends Assetholder implements NationMember, Teleportable {
    public Person(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.premium = false;
    }
    public Person(Person copy) {
        super(copy);

        this.premium = copy.premium;
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
    }

    @Override
    public PersonData toData() {
        PersonData data = new PersonData(super.toData());

        data.premium = premium;
        data.premiumExpiration = premiumExpiration;

        return data;
    }

    public void load(PersonData data, LedgerState state) {
        super.load(data, state);

        this.premium = data.premium;
        this.premiumExpiration = data.premiumExpiration;
    }

    // Utils
    @Nullable
    public Player toPlayer() {
        return Bukkit.getPlayer(getUniqueId());
    }

    // Premium
    private boolean premium;
    private Date premiumExpiration;

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
