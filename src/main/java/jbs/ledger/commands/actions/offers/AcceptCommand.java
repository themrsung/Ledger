package jbs.ledger.commands.actions.offers;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A contextual accept command. Can be used to accept any offer or request.
 */
public final class AcceptCommand extends LedgerPlayerCommand {
    public AcceptCommand(Ledger ledger) {
        super(ledger);
    }
    public AcceptCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        HandleOffersCommand command = new HandleOffersCommand(this, getActor());
        command.onAcceptCommand(mainArg, argsAfterMain);
    }
}

