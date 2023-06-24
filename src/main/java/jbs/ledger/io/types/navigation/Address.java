package jbs.ledger.io.types.navigation;

import org.bukkit.Location;

public class Address {
    public Address(Location location) {
        assert location.getWorld() != null;

        this.world = location.getWorld().getName();

        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();

        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }
    public Address() {}

    public String world;
    public double x;
    public double y;
    public double z;

    public float yaw;
    public float pitch;
}
