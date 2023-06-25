package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class BuyCommand extends LedgerPlayerCommand {
    public BuyCommand(Ledger ledger) {
        super(ledger);
    }
    public BuyCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        BuyOrSellCommand buyOrSell = new BuyOrSellCommand(this, getActor());
        buyOrSell.onBuyCommand(mainArg, argsAfterMain);
    }
}
