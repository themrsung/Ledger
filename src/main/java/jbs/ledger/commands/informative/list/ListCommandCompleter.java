package jbs.ledger.commands.informative.list;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ListCommandCompleter extends LedgerCommandAutoCompleter {
    public ListCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public ListCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.MARKETS);
            list.addAll(LedgerCommandKeywords.EXCHANGES);
            list.addAll(LedgerCommandKeywords.PLAYERS);
            list.addAll(LedgerCommandKeywords.CORPORATES);
            list.addAll(LedgerCommandKeywords.FOUNDATIONS_GENERIC);
            list.addAll(LedgerCommandKeywords.SOVEREIGNS);
            list.addAll(LedgerCommandKeywords.TRUSTS_GENERIC);
        }

        return list;
    }
}
