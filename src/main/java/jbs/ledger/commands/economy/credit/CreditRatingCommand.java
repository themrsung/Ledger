package jbs.ledger.commands.economy.credit;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class CreditRatingCommand extends LedgerPlayerCommand {
    public CreditRatingCommand(Ledger ledger) {
        super(ledger);
    }

    public CreditRatingCommand(LedgerPlayerCommand command, Assetholder actor) {
        super(command, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        Assetholder holder = getActor();

        if (mainArg != null) {
            holder = getState().getAssetholder(mainArg, true, true);
        }

        if (holder == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        getMessenger().creditRating(holder);
    }
}
