package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.messages.DirectMessage;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public final class ReplyCommand extends LedgerPlayerCommand {
    public ReplyCommand(Ledger ledger) {
        super(ledger);
    }
    public ReplyCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        ArrayList<DirectMessage> messages = getState().getMessagesByRecipient(getPerson());
        if (messages.size() < 1) {
            getMessenger().noMessagesReceived();
            return;
        } else if (messages.size() > 1) {
            Assetholder filter = getState().getAssetholder(mainArg, true, true);
            for (DirectMessage dm : messages) {
                if (dm.sender != null && dm.sender.equals(filter)) {


                    String content = String.join(" ", argsAfterMain);
                    if (getPerson().isPremium()) {
                        content = content.replaceAll("&", "§");
                    }

                    DirectMessage message = new DirectMessage(
                            getPerson(),
                            dm.sender,
                            content
                    );
                    getState().addMessage(message);
                    getMessenger().custom("[본인 -> " + dm.sender.getName() + "] " + content);
                    return;
                }
            }

        }



        String content = mainArg + " " + String.join(" ", argsAfterMain);
        if (getPerson().isPremium()) {
            content = content.replaceAll("&", "§");
        }

        Person recipient = messages.get(0).sender;
        DirectMessage message = new DirectMessage(
                getPerson(),
                recipient,
                content
        );

        getState().addMessage(message);
        getMessenger().custom("[본인 -> " + recipient.getName() + "] " + content);
    }
}
