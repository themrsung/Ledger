package jbs.ledger.commands.economy.credit;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CreditRatingCommandCompleter extends LedgerCommandAutoCompleter {
    public CreditRatingCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public CreditRatingCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }
    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length < 2) {
            for (Assetholder h : getState().getAssetholders()) {
                results.add(h.getSearchTag());
            }
        }

        return results;
    }
}
