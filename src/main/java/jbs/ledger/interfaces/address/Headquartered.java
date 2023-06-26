package jbs.ledger.interfaces.address;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.common.Unique;
import org.bukkit.Location;

import javax.annotation.Nullable;

public interface Headquartered extends Unique {
    @Nullable
    Location getAddress();
    void setAddress(@Nullable Location address);

    @Nullable
    Location getPreviousLocation();
    void setPreviousLocation(@Nullable Location address);

    boolean hasPropertyAccess(Person person);

    long getProtectionRadius();
}
