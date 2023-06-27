package jbs.ledger.commands.economy.options;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class OptionsCommand extends LedgerPlayerCommand {
    public OptionsCommand(Ledger ledger) {
        super(ledger);
    }
    public OptionsCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        getMessenger().featureUnderDevelopment();
    }
}
