package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class NoCommand extends LedgerPlayerCommand {
    public NoCommand(Ledger ledger) {
        super(ledger);
    }
    public NoCommand(LedgerPlayerCommand originalCommand, Economic actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {

    }
}
