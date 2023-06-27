package jbs.ledger.commands.economy.networth;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class NetWorthCommand extends LedgerPlayerCommand {
    public NetWorthCommand(Ledger ledger) {
        super(ledger);
    }
    public NetWorthCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        String denotation = getConfig().DEFAULT_CURRENCY;

        if (mainArg != null) {
            if (getState().isCurrency(mainArg)) {
                denotation = mainArg.toUpperCase();
            } else {
                Assetholder a = getState().getAssetholder(mainArg, true, true);

                if (a == null) {
                    getMessenger().assetholderNotFound();
                    return;
                }

                if (argsAfterMain.length >= 1 && getState().isCurrency(argsAfterMain[0])) {
                    denotation = argsAfterMain[0].toUpperCase();
                }

                getMessenger().netWorthList(a, denotation, getState());

                return;
            }
        }

        getMessenger().netWorthList(getActor(), denotation, getState());
    }
}
