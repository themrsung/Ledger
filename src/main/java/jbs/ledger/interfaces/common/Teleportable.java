package jbs.ledger.interfaces.common;

import org.bukkit.Location;

public interface Teleportable {
    /**
     * Teleports this teleportable.
     * @param address Address to teleport to.
     */
    void teleport(Location address);
}
