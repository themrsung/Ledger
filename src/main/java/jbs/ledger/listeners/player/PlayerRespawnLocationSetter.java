package jbs.ledger.listeners.player;

import jbs.ledger.Ledger;
import jbs.ledger.io.types.navigation.Address;
import jbs.ledger.listeners.LedgerListener;
import jbs.ledger.utils.TypeUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnLocationSetter extends LedgerListener {
    public PlayerRespawnLocationSetter(Ledger ledger) {
        super(ledger);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Address spawn = getState().getConfig().serverSpawn;
        if (spawn == null) return;

        Location location = TypeUtils.addressToLocation(spawn);
        if (location == null) return;

        e.setRespawnLocation(location);
    }
}
