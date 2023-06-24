package jbs.ledger.classes.navigation;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.messenger.LedgerPlayerMessenger;
import jbs.ledger.state.LedgerState;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Date;

public class TeleportRequest {
    public TeleportRequest(
            Person from,
            Person to
    ) {
        this.from = from;
        this.to = to;
        this.date = new Date();


    }

    public final Person from;
    public final Person to;
    public final Date date;
    @Nullable
    protected Location getDestination() {
        Player p = to.toPlayer();
        if (p == null) return null;

        return p.getLocation();
    }

    public Person getPersonToTeleport() {
        return from;
    }

    public void onAccepted(LedgerState state) {
        assert to.toPlayer() != null;

        getPersonToTeleport().teleport(getDestination());
        LedgerPlayerMessenger messenger = LedgerPlayerMessenger.getInstance(to.toPlayer());
        messenger.teleportSucceeded();

        state.removeTeleportRequest(this);
    }

    public void onDeclined(LedgerState state) {
        assert getPersonToTeleport().toPlayer() != null;

        LedgerPlayerMessenger messenger = LedgerPlayerMessenger.getInstance(to.toPlayer());
        messenger.teleportRequestDeclined(from);

        state.removeTeleportRequest(this);
    }
}
