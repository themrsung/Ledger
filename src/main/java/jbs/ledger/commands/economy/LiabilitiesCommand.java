package jbs.ledger.commands.economy;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class LiabilitiesCommand extends LedgerPlayerCommand {
    public LiabilitiesCommand(Ledger ledger) {
        super(ledger);
    }
    public LiabilitiesCommand(LedgerPlayerCommand originalCommand, Economic actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {

    }
}
