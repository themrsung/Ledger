package jbs.ledger.commands.economy.assets;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.economy.balance.BalanceCommandCompleter;
import jbs.ledger.commands.economy.bank.BankCommandCompleter;
import jbs.ledger.commands.economy.bonds.BondsCommandCompleter;
import jbs.ledger.commands.economy.cards.CardsCommandCompleter;
import jbs.ledger.commands.economy.forwards.ForwardsCommandCompleter;
import jbs.ledger.commands.economy.futures.FuturesCommandCompleter;
import jbs.ledger.commands.economy.options.OptionsCommandCompleter;
import jbs.ledger.commands.economy.stocks.StocksCommandCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class AssetsCommandCompleter extends LedgerCommandAutoCompleter {
    public AssetsCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public AssetsCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.BALANCE);
            list.addAll(LedgerCommandKeywords.BANK);
            list.addAll(LedgerCommandKeywords.FORWARDS);
            list.addAll(LedgerCommandKeywords.FUTURES);
            list.addAll(LedgerCommandKeywords.OPTIONS);
            list.addAll(LedgerCommandKeywords.STOCKS);
            list.addAll(LedgerCommandKeywords.CARDS);

            list.addAll(getState().getCurrencies());
        } else {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.BALANCE.contains(action)) {
                BalanceCommandCompleter bcc = new BalanceCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.BANK.contains(action)) {
                BankCommandCompleter bcc = new BankCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.BONDS.contains(action)) {
                BondsCommandCompleter bcc = new BondsCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.CARDS.contains(action)) {
                CardsCommandCompleter ccc = new CardsCommandCompleter(this);
                return ccc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.FORWARDS.contains(action)) {
                ForwardsCommandCompleter fcc = new ForwardsCommandCompleter(this);
                return fcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.FUTURES.contains(action)) {
                FuturesCommandCompleter fcc = new FuturesCommandCompleter(this);
                return fcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.OPTIONS.contains(action)) {
                OptionsCommandCompleter occ = new OptionsCommandCompleter(this);
                return occ.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.STOCKS.contains(action)) {
                StocksCommandCompleter scc = new StocksCommandCompleter(this);
                return scc.onSudoComplete(args, getActor());
            }

            list.addAll(getState().getCurrencies());
        }

        return list;
    }
}
