package jbs.ledger.commands.actions.teleportation;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class BackCommand extends LedgerPlayerCommand {
    public BackCommand(Ledger ledger) {
        super(ledger);
    }
    public BackCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        Location previous = getPerson().getPreviousLocation();
        if (previous == null) {
            getMessenger().invalidTeleportDestination();
            return;
        }

        getPlayer().teleport(previous);
        getMessenger().teleportSucceeded();
    }
}
