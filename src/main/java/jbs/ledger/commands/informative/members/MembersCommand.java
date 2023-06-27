package jbs.ledger.commands.informative.members;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.organization.Organization;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MembersCommand extends LedgerPlayerCommand {
    public MembersCommand(Ledger ledger) {
        super(ledger);
    }
    public MembersCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (!(getActor() instanceof Organization<?>)) {
            getMessenger().actorNotOrganization();
            return;
        }

        getMessenger().membersListHeader();

        Organization<?> o = (Organization<?>) getActor();
        for (Unique m : o.getMembers()) {
            if (m instanceof Assetholder) {
                Assetholder a = (Assetholder) m;
                getMessenger().membersListEntry(a);
            }
        }
    }
}
