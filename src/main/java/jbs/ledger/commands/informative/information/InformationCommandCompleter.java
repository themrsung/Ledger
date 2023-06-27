package jbs.ledger.commands.informative.information;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class InformationCommandCompleter extends LedgerCommandAutoCompleter {
    public InformationCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public InformationCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        return list;
    }
}
