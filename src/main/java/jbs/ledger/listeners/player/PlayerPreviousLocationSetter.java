package jbs.ledger.listeners.player;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.listeners.LedgerListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPreviousLocationSetter extends LedgerListener {
    public PlayerPreviousLocationSetter(Ledger ledger) {
        super(ledger);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Person p = getState().getPerson(e.getPlayer().getUniqueId());
        if (p == null) return;

        if (!(e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND || e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN)) return;

        p.setPreviousLocation(e.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Person p = getState().getPerson(e.getEntity().getUniqueId());
        if (p == null) return;

        p.setPreviousLocation(e.getEntity().getLocation());
    }
}
