package jbs.ledger.commands.economy.pay;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class PayCommand extends LedgerPlayerCommand {
    public PayCommand(Ledger ledger) {
        super(ledger);
    }
    public PayCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 1) {
            getMessenger().insufficientArgs();
            return;
        }

        Assetholder recipient = getState().getAssetholder(mainArg, true, true);
        Cash payment = Cash.fromInput(argsAfterMain[0], getState());

        if (recipient == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        assert getActor() != null;

        if (!getActor().getCash().contains(payment)) {
            getMessenger().insufficientCash();
            return;
        }

        Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                getActor(),
                recipient,
                payment,
                "Pay command"
        ));
        getMessenger().cashSent(recipient, payment);
    }
}
