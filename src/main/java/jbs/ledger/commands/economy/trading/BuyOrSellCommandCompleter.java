package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.List;

public final class BuyOrSellCommandCompleter extends LedgerCommandAutoCompleter {
    public BuyOrSellCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        return null;
    }
}
