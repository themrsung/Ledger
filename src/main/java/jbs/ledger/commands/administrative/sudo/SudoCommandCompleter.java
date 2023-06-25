package jbs.ledger.commands.administrative.sudo;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.actions.create.CreateCommandCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SudoCommandCompleter extends LedgerCommandAutoCompleter {
    public SudoCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length < 2) {
            for (Assetholder h : getState().getAssetholders()) {
                results.add(h.getSearchTag());
            }
        } else if (args.length < 3) {
            results.addAll(LedgerCommandKeywords.PAY);
            results.addAll(LedgerCommandKeywords.CREATE);
            results.addAll(LedgerCommandKeywords.BALANCE);
            results.addAll(LedgerCommandKeywords.STOCKS);
            results.addAll(LedgerCommandKeywords.BONDS);
            results.addAll(LedgerCommandKeywords.FUTURES);
            results.addAll(LedgerCommandKeywords.FORWARDS);
            results.addAll(LedgerCommandKeywords.INVITE);
            results.addAll(LedgerCommandKeywords.KICK);
            results.addAll(LedgerCommandKeywords.PUNISH);
            results.addAll(LedgerCommandKeywords.ACCEPT);
            results.addAll(LedgerCommandKeywords.DENY);
            results.addAll(LedgerCommandKeywords.LIST);
            results.addAll(LedgerCommandKeywords.MEMBERS);
            results.addAll(LedgerCommandKeywords.DIRECTORS);
            results.addAll(LedgerCommandKeywords.NET_WORTH);
            results.addAll(LedgerCommandKeywords.ASSETS);
            results.addAll(LedgerCommandKeywords.LIABILITIES);
            results.addAll(LedgerCommandKeywords.VOTE);
            results.addAll(LedgerCommandKeywords.HOME);
            results.addAll(LedgerCommandKeywords.SET_HOME);
            results.addAll(LedgerCommandKeywords.DELETE_HOME);
            results.addAll(LedgerCommandKeywords.MANAGE);
            results.addAll(LedgerCommandKeywords.BANK);
            results.addAll(LedgerCommandKeywords.CANCEL);
            results.addAll(LedgerCommandKeywords.SUDO);
            results.addAll(LedgerCommandKeywords.OPTIONS);
            results.addAll(LedgerCommandKeywords.CREDIT_RATING);
            results.addAll(LedgerCommandKeywords.BUY);
            results.addAll(LedgerCommandKeywords.SELL);
        } else  {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.CREATE.contains(action)) {
                CreateCommandCompleter ccc = new CreateCommandCompleter(getLedger());
                return ccc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.SUDO.contains(action)){
                SudoCommandCompleter scc = new SudoCommandCompleter(getLedger());
                return scc.onSudoComplete(args);
            }
        }

        return results;
    }
}
