package jbs.ledger.commands.actions.home;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class HomeCommand extends LedgerPlayerCommand {
    public HomeCommand(Ledger ledger) {
        super(ledger);
    }
    public HomeCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        Location address = getActor().getAddress();
        if (address == null) {
            getMessenger().invalidTeleportDestination();
            return;
        }

        getPerson().teleport(address);
        getMessenger().teleportSucceeded();
    }
}
