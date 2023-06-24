package jbs.ledger;

import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.listeners.bookkeeping.AssetTransferHandler;
import jbs.ledger.listeners.player.PlayerProfileUpdater;
import jbs.ledger.state.LedgerState;
import jbs.ledger.timers.LedgerAutoSaver;
import jbs.ledger.timers.LedgerNoteHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ledger extends JavaPlugin {
    public static String VERSION = "1.0";

    @Override
    public void onEnable() {
        load();

        if (state == null) {
            state = new LedgerState();
        }

        new AssetTransferHandler(this);
        new PlayerProfileUpdater(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerAutoSaver(this), 20, 1200);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerNoteHandler(this), 30, 360);

        Bukkit.getLogger().info("Ledger v" + VERSION + " is loaded.");
    }

    @Override
    public void onDisable() {
        save();
    }

    private LedgerState state = null;
    public LedgerState getState() {
        return state;
    }

    public void save() {
        LedgerSaveState saveState = new LedgerSaveState(state);
        saveState.save();
    }

    public void load() {
        LedgerSaveState saveState = LedgerSaveState.load();

        if (saveState != null) {
            state = new LedgerState(saveState);
        }
    }
}
