package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A contextual cancel command. Can be used to cancel any offer or request.
 */
public final class CancelCommand extends LedgerPlayerCommand {
    public CancelCommand(Ledger ledger) {
        super(ledger);
    }
    public CancelCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        HandleOffersCommand command = new HandleOffersCommand(this, getActor());
        command.onCancelCommand(mainArg, argsAfterMain);
    }
}

