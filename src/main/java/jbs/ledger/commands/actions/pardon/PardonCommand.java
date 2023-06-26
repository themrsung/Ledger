package jbs.ledger.commands.actions.pardon;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.special.SovereignCorporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.sovereignty.Sovereign;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class PardonCommand extends LedgerPlayerCommand {
    public PardonCommand(Ledger ledger) {
        super(ledger);
    }
    public PardonCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        if (!(getActor() instanceof Sovereign)) {
            getMessenger().commandOnlyExecutableByNations();
            return;
        }

        Person p = getState().getPerson(mainArg);
        if (p == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        Sovereign s = (Sovereign) getActor();

        if (s.removeBannedPlayer(p) || s.removeMutedPlayer(p)) {
            getMessenger().playerPardoned();
        } else {
            getMessenger().playerNotPardonable();
        }
    }
}
