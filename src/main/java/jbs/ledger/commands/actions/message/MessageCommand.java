package jbs.ledger.commands.actions.message;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.messages.DirectMessage;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.organization.Organization;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public final class MessageCommand extends LedgerPlayerCommand {
    public MessageCommand(Ledger ledger) {
        super(ledger);
    }
    public MessageCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 1) {
            getMessenger().insufficientArgs();
            return;
        }

        Assetholder holder = getState().getAssetholder(mainArg, true, true);
        String content = String.join(" ", argsAfterMain);

        if (getPerson().isPremium()) {
            content = content.replace("&", "§");
        }

        Person recipient = (holder instanceof Person) ? (Person) holder : null;

        if (recipient == null && holder instanceof Organization<?>) {
            // For organizations of people (Corporations, Nations, etc.)
            Organization<?> o = (Organization<?>) holder;
            if (o.getRepresentative() instanceof Person) {
                recipient = (Person) o.getRepresentative();
            } else if (o.getRepresentative() instanceof Organization<?>) {
                // For federations
                Organization<?> o2 = (Organization<?>) o.getRepresentative();
                if (o2.getRepresentative() instanceof Person) {
                    recipient = (Person) o2.getRepresentative();
                }
            }
        }

        if (recipient == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        DirectMessage message = new DirectMessage(
                getPerson(),
                recipient,
                content
        );

        // @TODO Make this an event

        getMessenger().custom("[본인 -> " + recipient.getName() + "] " + content);

        getState().addMessage(message);
    }
}
