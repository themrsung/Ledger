package jbs.ledger.commands.economy.balance;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public final class BalanceCommand extends LedgerPlayerCommand {
    public BalanceCommand(Ledger ledger) {
        super(ledger);
    }
    public BalanceCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        ArrayList<String> currencies = getState().getCurrencies();

        assert getActor() != null;

        if (mainArg != null) {
            currencies.removeIf(c -> !c.equalsIgnoreCase(mainArg));
        }

        getMessenger().balanceInformationHeader();

        for (String c : currencies) {
            getMessenger().balanceInformation(getActor().getCash().get(c));
        }
    }
}
