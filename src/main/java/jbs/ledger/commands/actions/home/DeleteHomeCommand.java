package jbs.ledger.commands.actions.home;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class DeleteHomeCommand extends LedgerPlayerCommand {
    public DeleteHomeCommand(Ledger ledger) {
        super(ledger);
    }
    public DeleteHomeCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        getActor().setAddress(null);
        getMessenger().addressDeleted();
    }
}
