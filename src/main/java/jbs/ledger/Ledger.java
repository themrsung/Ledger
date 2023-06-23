package jbs.ledger;

import jbs.ledger.classes.Assetholder;
import jbs.ledger.events.transfers.forwards.StockForwardTransferredEvent;
import jbs.ledger.events.transfers.futures.StockFuturesTransferredEvent;
import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.listeners.AssetTransferHandler;
import jbs.ledger.state.LedgerState;
import jbs.ledger.timers.LedgerAutoSaver;
import jbs.ledger.timers.LedgerNoteExpirationHandler;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.assets.synthetic.UniqueNote;
import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.UUID;

public final class Ledger extends JavaPlugin {
    public static String VERSION = "1.0";

    @Override
    public void onEnable() {
        load();

        if (state == null) {
            state = new LedgerState();
        }

        new AssetTransferHandler(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerAutoSaver(this), 20, 1200);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerNoteExpirationHandler(this), 30, 360);

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
