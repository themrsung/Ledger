package jbs.ledger.commands;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.state.LedgerState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class LedgerCommandAutoCompleter implements TabCompleter {
    public LedgerCommandAutoCompleter(Ledger ledger) {
        this.ledger = ledger;
    }
    private final Ledger ledger;
    protected Ledger getLedger() {
        return ledger;
    }

    protected LedgerState getState() {
        return getLedger().getState();
    }

    private CommandSender sender;

    private CommandSender getSender() {
        return sender;
    }

    @Nonnull
    protected Player getPlayer() {
        return (Player) sender;
    }

    @Nonnull
    protected Assetholder getActor() {
        return Objects.requireNonNull(getState().getAssetholder(getPlayer().getUniqueId()));
    }

    @Nonnull
    protected Person getPerson() {
        return (Person) getActor();
    }

    private boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    @Override
    public final List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        String cmd = command.getName().toLowerCase();
        this.sender = sender;

        if (isConsole()) return new ArrayList<>();

        return onLedgerTabComplete(cmd, args);
    }

    public final List<String> onSudoComplete(@Nonnull String[] originalArgs) {
        String command = originalArgs.length > 3 ? originalArgs[2] : "";
        String[] args = originalArgs.length > 3 ? Arrays.copyOfRange(originalArgs, 2, originalArgs.length) : new String[] {};

        return onLedgerTabComplete(command, args);
    }

    protected abstract List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args);
}
