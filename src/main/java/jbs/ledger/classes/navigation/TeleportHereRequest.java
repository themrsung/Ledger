package jbs.ledger.classes.navigation;

import jbs.ledger.assetholders.person.Person;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Date;

public final class TeleportHereRequest extends TeleportRequest {
    public TeleportHereRequest(
            Person from,
            Person to
    ) {
        super(from, to);
    }

    @Nullable
    @Override
    protected Location getDestination() {
        Player p = from.toPlayer();
        if (p == null) return null;

        return p.getLocation();
    }

    @Override
    public Person getPersonToTeleport() {
        return to;
    }
}
