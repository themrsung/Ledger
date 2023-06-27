package jbs.ledger.io.types.cards;

import java.util.Date;
import java.util.UUID;

public final class DebitCardData {
    public DebitCardData() {}

    public UUID uniqueId;
    public UUID issuer;
    public UUID holder;
    public String currency;
    public UUID linkedAccount;
    public Date expiration;
    public boolean active;
}
