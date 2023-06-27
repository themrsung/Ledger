package jbs.ledger.commands.economy.bank;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class BankCommand extends LedgerPlayerCommand {
    public BankCommand(Ledger ledger) {
        super(ledger);
    }
    public BankCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        if (LedgerCommandKeywords.DEPOSIT.contains(mainArg)) {
            if (argsAfterMain.length < 2) {
                getMessenger().insufficientArgs();
                return;
            }

            Bank bank = getState().getBank(argsAfterMain[0]);
            Cash amount = Cash.fromInput(argsAfterMain[1], getState());

            if (bank == null) {
                getMessenger().assetholderNotFound();
                return;
            }

            if (amount.getBalance() <= 0d) {
                getMessenger().invalidMoney();
                return;
            }

            if (!getActor().getCash().contains(amount)) {
                getMessenger().insufficientCash();
                return;
            }

            bank.deposit(getActor(), amount);
            getMessenger().bankDepositSuccessful();
            return;
        } else if (LedgerCommandKeywords.WITHDRAW.contains(mainArg)) {
            if (argsAfterMain.length < 2) {
                getMessenger().insufficientArgs();
                return;
            }

            Bank bank = getState().getBank(argsAfterMain[0]);
            Cash amount = Cash.fromInput(argsAfterMain[1], getState());

            if (bank == null) {
                getMessenger().assetholderNotFound();
                return;
            }

            if (amount.getBalance() <= 0d) {
                getMessenger().invalidMoney();
                return;
            }

            if (!bank.canWithdraw(getActor(), amount)) {
                getMessenger().insufficientCash();
                return;
            }

            bank.withdraw(getActor(), amount);
            getMessenger().bankWithdrawalSuccessful();
            return;
        } else if (LedgerCommandKeywords.BALANCE.contains(mainArg)) {
            if (argsAfterMain.length < 1) {
                getMessenger().bankBalanceListHeader();

                for (Bank b : getState().getBanks()) {
                    for (Account<Cash> account : b.getAccounts(getActor())) {
                        getMessenger().bankBalanceInformation(b, account);
                    }
                }

                return;
            }

            Bank b = getState().getBank(argsAfterMain[0]);
            if (b == null) {
                getMessenger().assetholderNotFound();
                return;
            }

            getMessenger().bankBalanceListHeader();

            for (Account<Cash> ba : b.getAccounts(getActor())) {
                getMessenger().bankBalanceInformation(b, ba);
            }

            return;
        }


        getMessenger().invalidKeyword();
    }
}
