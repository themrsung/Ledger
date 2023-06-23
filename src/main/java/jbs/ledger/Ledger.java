package jbs.ledger;

import jbs.ledger.listeners.AssetTransferHandler;
import jbs.ledger.state.LedgerState;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ledger extends JavaPlugin {
    public static String VERSION = "1.0";

    private LedgerState state;
    public LedgerState getState() {
        return state;
    }

    @Override
    public void onEnable() {
        state = new LedgerState();

        new AssetTransferHandler(this);

        Bukkit.getLogger().info("Ledger v" + VERSION + " is loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
