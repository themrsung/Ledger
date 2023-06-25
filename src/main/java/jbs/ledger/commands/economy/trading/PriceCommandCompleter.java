package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.interfaces.markets.Market;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class PriceCommandCompleter extends LedgerCommandAutoCompleter {
    public PriceCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            ArrayList<Market<?>> markets = getState().getMarkets();
            for (Market<?> m : markets) {
                list.add(m.getSymbol());
            }
        }

        return list;
    }
}
