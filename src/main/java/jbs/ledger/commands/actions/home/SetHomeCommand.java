package jbs.ledger.commands.actions.home;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SetHomeCommand extends LedgerPlayerCommand {
    public SetHomeCommand(Ledger ledger) {
        super(ledger);
    }
    public SetHomeCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        Location location = getPlayer().getLocation();
        getActor().setAddress(location);
        getMessenger().addressSet();
    }
}
