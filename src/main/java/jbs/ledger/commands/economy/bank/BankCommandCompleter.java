package jbs.ledger.commands.economy.bank;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.classes.banking.BankAccount;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.types.assets.basic.Cash;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class BankCommandCompleter extends LedgerCommandAutoCompleter {
    public BankCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public BankCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.DEPOSIT);
            list.addAll(LedgerCommandKeywords.WITHDRAW);
            list.addAll(LedgerCommandKeywords.BALANCE);
        } else if (args.length < 3) {
            for (Bank b : getState().getBanks()) {
                list.add(b.getSearchTag());
            }
        } else if (args.length < 4) {
            String action = args[1].toLowerCase();
            Bank b = getState().getBank(args[2]);

            if (b != null) {
                String currency = b.getPreferredCurrency();

                if (LedgerCommandKeywords.DEPOSIT.contains(action)) {
                    Cash balance = getActor().getCash().get(currency);
                    if (balance != null) list.add(balance.format());
                } else if (LedgerCommandKeywords.WITHDRAW.contains(action)) {
                    for (Account<Cash> account : b.getAccounts(getActor())) {
                        list.add(account.getContent().format());
                    }
                }
            }
        }

        return list;
    }
}
