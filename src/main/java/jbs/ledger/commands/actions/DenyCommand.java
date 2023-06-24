package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A contextual deny command. Can be used to deny any offer of request.
 */
public final class DenyCommand extends LedgerPlayerCommand {
    public DenyCommand(Ledger ledger) {
        super(ledger);
    }
    public DenyCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        HandleOffersCommand command = new HandleOffersCommand(this, getActor());
        command.onDenyCommand(mainArg, argsAfterMain);
    }
}
