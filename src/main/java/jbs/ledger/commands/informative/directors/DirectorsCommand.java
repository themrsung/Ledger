package jbs.ledger.commands.informative.directors;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.organization.Directorship;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class DirectorsCommand extends LedgerPlayerCommand {
    public DirectorsCommand(Ledger ledger) {
        super(ledger);
    }
    public DirectorsCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (!(getActor() instanceof Directorship)) {
            getMessenger().actorNotDirectorship();
            return;
        }

        getMessenger().directorsListHeader();

        Directorship d = (Directorship) getActor();
        for (Person p : d.getBoard().getMembers()) {
            getMessenger().directorsListEntry(p);
        }
    }
}
