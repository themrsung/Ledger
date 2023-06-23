package jbs.ledger;

import jbs.ledger.classes.Account;
import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.listeners.AssetTransferHandler;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.Cash;
import jbs.ledger.types.assets.UniqueNote;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

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

        Account a1 = new Account(UUID.randomUUID(), UUID.randomUUID());
        Account a2 = new Account(UUID.randomUUID(), UUID.randomUUID());

        state.addAccount(a1);
        state.addAccount(a2);

        Cash c = new Cash("TEST", 2039120d);

        a1.getCash().add(c);
        a1.getCash().add(c);
        a1.getCash().add(c);

        Cash ca = a1.getCash().get("TEST");
        if (ca != null) {
            Bukkit.getLogger().info(ca.getBalance() + ca.getSymbol());
        }

        Cash ca1 = a1.getCash().get("TEST");
        if (ca1 != null) {
            Bukkit.getLogger().info(ca1.getBalance() + ca1.getSymbol());
        }

        Bukkit.getPluginManager().callEvent(new AssetTransferredEvent<>(
                a1,
                a2,
                c,
                "test 2"
        ));




        Cash ca2 = a2.getCash().get("TEST");
        if (ca2 != null) {
            Bukkit.getLogger().info(ca2.getBalance() + ca2.getSymbol());
        }



        Bukkit.getLogger().info("Ledger v" + VERSION + " is loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
