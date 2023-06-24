package jbs.ledger.commands;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.messenger.LedgerPlayerMessenger;
import jbs.ledger.state.LedgerState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * A better command executor
 */
public abstract class LedgerCommand implements CommandExecutor {
    public LedgerCommand(Ledger ledger) {
        this.ledger = ledger;
    }
    protected LedgerCommand(LedgerCommand command, Assetholder actor) {
        this.ledger = command.ledger;
        this.sender = command.sender;
        this.actor = actor;
        this.messenger = command.messenger;
    }

    private final Ledger ledger;
    private CommandSender sender;

    protected Ledger getLedger() {
        return ledger;
    }
    protected LedgerState getState() {
        return ledger.getState();
    }
    protected CommandSender getSender() {
        return sender;
    }

    protected boolean isConsole() {
        return getSender() instanceof ConsoleCommandSender;
    }

    @Nullable
    protected Player getPlayer() {
        if (getSender() instanceof Player) {
            return (Player) getSender();
        } else {
            return null;
        }
    }

    @Nullable
    protected Person getPerson() {
        if (getPlayer() == null) return null;
        return getState().getPerson(getPlayer().getUniqueId());
    }
    @Nullable
    private Assetholder actor;

    @Nullable
    protected Assetholder getActor() {
        return actor;
    }

    private LedgerPlayerMessenger messenger;

    protected LedgerPlayerMessenger getMessenger() {
        return messenger;
    }

    /**
     * Logs a message to console.
     * @param msg Message to send.
     */
    protected void log(String msg) {
        Bukkit.getLogger().info(msg);
    }

    protected boolean isSelf() {
        if (getActor() == null) return false;

        return getActor().equals(getPerson());
    }

    @Override
    public final boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        this.sender = sender;

        String mainArg = args.length >= 1 ? args[0].toLowerCase() : null;
        String[] argsAfterMain = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[] {};

        if (getPerson() != null) {
            this.actor = getPerson();
            this.messenger = LedgerPlayerMessenger.getInstance(getPlayer());
        }

        onLedgerCommand(mainArg, argsAfterMain);
        return true;
    }

    public void onSudoCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        onLedgerCommand(mainArg, argsAfterMain);
    }

    protected abstract void onLedgerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain);
}
