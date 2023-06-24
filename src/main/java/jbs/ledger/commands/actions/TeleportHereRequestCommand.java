package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.navigation.TeleportHereRequest;
import jbs.ledger.classes.navigation.TeleportRequest;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.messenger.LedgerPlayerMessenger;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class TeleportHereRequestCommand extends LedgerPlayerCommand {
    public TeleportHereRequestCommand(Ledger ledger) {
        super(ledger);
    }
    public TeleportHereRequestCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        Assetholder recipient = getState().getAssetholder(mainArg, true, true);
        if (recipient == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        if (!(recipient instanceof Person)) {
            // Instant teleportation
            Location hq = recipient.getAddress();
            if (hq == null) {
                getMessenger().invalidTeleportDestination();
                return;
            }

            getPlayer().teleport(hq);
            getMessenger().teleportSucceeded();
            return;
        }

        Person to = (Person) recipient;

        TeleportHereRequest request = new TeleportHereRequest(
                getPerson(),
                to
        );

        // TODO MAKE THIS AN event

        getState().addTeleportRequest(request);
        if (to.toPlayer() != null) {
            LedgerPlayerMessenger lpm = LedgerPlayerMessenger.getInstance(to.toPlayer());
            lpm.teleportRequestReceived(getPerson());
        }
        getMessenger().teleportRequestSent();

    }
}
