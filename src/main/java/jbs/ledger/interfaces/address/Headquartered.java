package jbs.ledger.interfaces.address;

import org.bukkit.Location;

import javax.annotation.Nullable;

public interface Headquartered {
    @Nullable
    Location getAddress();
    void setAddress(@Nullable Location address);

    @Nullable
    Location getPreviousLocation();
    void setPreviousLocation(@Nullable Location address);

    long getProtectionRadius();
}
