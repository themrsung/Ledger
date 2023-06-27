package jbs.ledger.commands.economy.pay;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class PayCommandCompleter extends LedgerCommandAutoCompleter {
    public PayCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public PayCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length < 2) {
            for (Assetholder h : getState().getAssetholders()) {
                results.add(h.getSearchTag());
            }

        } else if (args.length < 3) {
            results.add("금액을 입력하세요. (예: 23만 -> 230,000 CR / CR44k -> 44,000 CR)");
        }

        return results;
    }
}
