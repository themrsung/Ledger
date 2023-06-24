package jbs.ledger.commands.economy;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SellCommand extends LedgerPlayerCommand {
    public SellCommand(Ledger ledger) {
        super(ledger);
    }
    public SellCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        BuyOrSellCommand buyOrSell = new BuyOrSellCommand(this, getActor());
        buyOrSell.onSellCommand(mainArg, argsAfterMain);
    }
}
