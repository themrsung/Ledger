package jbs.ledger.commands.economy.stocks;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.types.assets.basic.Stock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class StocksCommand extends LedgerPlayerCommand {
    public StocksCommand(Ledger ledger) {
        super(ledger);
    }
    public StocksCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg != null) {
            Stock s = getActor().getStocks().get(mainArg);
            if (s == null) {
                getMessenger().stockNotFound();
                return;
            }

            getMessenger().stockInformation(s);
            return;
        }

        getMessenger().stockPortfolioHeader();

        for (Stock s : getActor().getStocks().get()) {
            getMessenger().stockInformation(s);
        }
    }
}
