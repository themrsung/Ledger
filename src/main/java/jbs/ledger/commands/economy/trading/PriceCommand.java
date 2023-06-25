package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.markets.Market;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class PriceCommand extends LedgerPlayerCommand {
    public PriceCommand(Ledger ledger) {
        super(ledger);
    }

    public PriceCommand(LedgerPlayerCommand command, Assetholder actor) {
        super(command, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        Market<?> market = getState().getMarket(mainArg);
        if (market == null) {
            getMessenger().marketNotFound();
            return;
        }

        getMessenger().marketPriceInformation(market);
    }
}
