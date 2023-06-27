package jbs.ledger.commands.actions.offers;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class HandleOffersCommandCompleter extends LedgerCommandAutoCompleter {
    public HandleOffersCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public HandleOffersCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }
    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length < 2) {
            results.addAll(LedgerCommandKeywords.ACCEPT);
            results.addAll(LedgerCommandKeywords.DENY);
            results.addAll(LedgerCommandKeywords.CANCEL);
        } else {
            String action = args[1].toLowerCase();

            if (args.length > 2) {
                String subAction = args[2].toLowerCase();

            }
        }

        return results;
    }
}

