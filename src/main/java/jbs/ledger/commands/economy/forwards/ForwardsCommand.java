package jbs.ledger.commands.economy.forwards;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ForwardsCommand extends LedgerPlayerCommand {
    public ForwardsCommand(Ledger ledger) {
        super(ledger);
    }
    public ForwardsCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        getMessenger().featureUnderDevelopment();

    }
}
