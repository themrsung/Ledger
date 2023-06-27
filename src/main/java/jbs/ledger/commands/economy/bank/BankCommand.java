package jbs.ledger.commands.economy.bank;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

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

            return;
        } else if (LedgerCommandKeywords.WITHDRAW.contains(mainArg)) {
            if (argsAfterMain.length < 2) {
                getMessenger().insufficientArgs();
                return;
            }

            return;
        } else if (LedgerCommandKeywords.BALANCE.contains(mainArg)) {
            if (argsAfterMain.length < 1) {
                // Loop all banks
                return;
            }
            // Get balance in specified bank
            return;
        }


        getMessenger().invalidKeyword();
    }
}
