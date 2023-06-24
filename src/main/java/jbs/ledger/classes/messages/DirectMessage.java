package jbs.ledger.classes.messages;

import jbs.ledger.assetholders.person.Person;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

public final class DirectMessage {
    public DirectMessage(
            @Nullable Person sender,
            @Nonnull Person recipient,
            @Nonnull String content
    ) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.date = new Date();
    }

    public final Person sender;
    public final Person recipient;
    public final String content;
    public final Date date;
    public boolean shown = false;

}
