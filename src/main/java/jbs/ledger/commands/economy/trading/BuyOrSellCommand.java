package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class BuyOrSellCommand extends LedgerPlayerCommand {
    public BuyOrSellCommand(Ledger ledger) {
        super(ledger);
    }
    public BuyOrSellCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    private boolean buy = false;

    public void onBuyCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        buy = true;
        onPlayerCommand(mainArg, argsAfterMain);
    }
    public void onSellCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        buy = false;
        onPlayerCommand(mainArg, argsAfterMain);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {

    }
}
