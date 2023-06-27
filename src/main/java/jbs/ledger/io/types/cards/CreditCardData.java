package jbs.ledger.io.types.cards;

import java.util.Date;
import java.util.UUID;

public final class CreditCardData {
    public CreditCardData() {}

    public UUID uniqueId;
    public UUID issuer;
    public UUID holder;
    public String currency;
    public double limit;
    public double balance;
    public Date expiration;
    public boolean active;
}
