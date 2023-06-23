package jbs.ledger.events;

import jbs.ledger.interfaces.common.Unique;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.UUID;

public class LedgerEvent extends Event implements Unique {
    public LedgerEvent() {
        super();

        this.uniqueId = UUID.randomUUID();
        this.date = new Date();
    }

    private final UUID uniqueId;
    private final Date date;

    // Identification
    public UUID getUniqueId() {
        return uniqueId;
    }
    public Date getDate() {
        return date;
    }

    // Boilerplate code
    private static final HandlerList handlers = new HandlerList();
    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
