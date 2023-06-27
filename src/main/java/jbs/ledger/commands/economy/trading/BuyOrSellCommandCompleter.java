package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.interfaces.markets.Market;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class BuyOrSellCommandCompleter extends LedgerCommandAutoCompleter {
    public BuyOrSellCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public BuyOrSellCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            ArrayList<Market<?>> markets = getState().getMarkets();
            for (Market<?> m : markets) {
                list.add(m.getSymbol());
            }
        } else if (args.length < 3) {
            Market<?> market = getState().getMarket(args[1]);
            list.add("가격 (지정가)");
            list.add("수량 (시장가)");
            if (market != null) list.add("거래통화: " + market.getCurrency());
        } else if (args.length < 4) {
            list.add("수량");
        }

        return list;
    }
}
