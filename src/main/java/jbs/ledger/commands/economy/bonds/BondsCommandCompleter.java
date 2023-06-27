package jbs.ledger.commands.economy.bonds;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class BondsCommandCompleter extends LedgerCommandAutoCompleter {
    public BondsCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public BondsCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        return list;
    }
}
