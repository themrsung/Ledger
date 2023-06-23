package jbs.ledger.listeners;

import jbs.ledger.Ledger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Base class for Listeners
 */
public abstract class LedgerListener implements Listener {
    public LedgerListener(Ledger ledger) {
        Bukkit.getPluginManager().registerEvents(this, ledger);
        this.ledger = ledger;
    }

    private final Ledger ledger;

    protected Ledger getLedger() {
        return ledger;
    }
}
