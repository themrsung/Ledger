package jbs.ledger.commands.economy.balance;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class BalanceCommandCompleter extends LedgerCommandAutoCompleter {
    public BalanceCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public BalanceCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }
    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        if (args.length < 2) {
            return getState().getCurrencies();
        }

        return new ArrayList<>();
    }
}
