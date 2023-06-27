package jbs.ledger.commands.economy.liabilities;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.economy.balance.BalanceCommandCompleter;
import jbs.ledger.commands.economy.cards.CardsCommandCompleter;
import jbs.ledger.commands.economy.futures.FuturesCommandCompleter;
import jbs.ledger.commands.economy.options.OptionsCommandCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class LiabilitiesCommandCompleter extends LedgerCommandAutoCompleter {
    public LiabilitiesCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public LiabilitiesCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.FORWARDS);
            list.addAll(LedgerCommandKeywords.FUTURES);
            list.addAll(LedgerCommandKeywords.OPTIONS);
            list.addAll(LedgerCommandKeywords.CARDS);
        } else {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.BALANCE.contains(action)) {
                BalanceCommandCompleter bcc = new BalanceCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.FUTURES.contains(action)) {
                FuturesCommandCompleter fcc = new FuturesCommandCompleter(this);
                return fcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.OPTIONS.contains(action)) {
                OptionsCommandCompleter occ = new OptionsCommandCompleter(this);
                return occ.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.CARDS.contains(action)) {
                CardsCommandCompleter ccc = new CardsCommandCompleter(this);
                return ccc.onSudoComplete(args, getActor());
            }
        }

        return list;
    }
}
