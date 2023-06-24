package jbs.ledger.utils;

import jbs.ledger.io.types.navigation.Address;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;

public abstract class TypeUtils {
    @Nullable
    public static Location addressToLocation(Address address) {
        World w = Bukkit.getWorld(address.world);
        if (w == null) return null;

        return new Location(
                w,
                address.x,
                address.y,
                address.z,
                address.yaw,
                address.pitch
        );
    }
}
