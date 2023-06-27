package jbs.ledger.commands.administrative.manage;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class ManageCommandCompleter extends LedgerCommandAutoCompleter {
    public ManageCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public ManageCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.LIST_ASSET);
            list.addAll(LedgerCommandKeywords.LISTED_ASSETS);
            list.addAll(LedgerCommandKeywords.DELIST_ASSET);
            list.addAll((LedgerCommandKeywords.SET_INTEREST_RATE));
            list.addAll((LedgerCommandKeywords.SET_BUYER_FEE_RATE));
            list.addAll((LedgerCommandKeywords.SET_SELLER_FEE_RATE));
        }

        return list;
    }
}
