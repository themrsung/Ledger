package jbs.ledger.commands.economy.stocks;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.types.assets.basic.Stock;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class StocksCommandCompleter extends LedgerCommandAutoCompleter {
    public StocksCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public StocksCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        for (Stock s : getActor().getStocks().get()) {
            list.add(s.getSymbol());
        }

        return list;
    }
}
