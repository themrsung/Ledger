package jbs.ledger.classes.invitation;

import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.organization.Organization;

public abstract class Invitation<M extends Unique, O extends Organization<?>> {
    public Invitation(O from, M to) {
        this.from = from;
        this.to = to;
    }
    public final O from;
    public final M to;

    public abstract void onAccepted();
    public abstract void onDeclined();

    public abstract String getDisplayName();
}
