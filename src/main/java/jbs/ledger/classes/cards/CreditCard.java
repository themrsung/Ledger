package jbs.ledger.classes.cards;

import jbs.ledger.assetholders.corporations.finance.CreditCardCompany;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.cards.CardIssuer;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.io.types.cards.CreditCardData;
import jbs.ledger.state.LedgerState;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.UUID;

public final class CreditCard implements Card {
    public CreditCard(
            CreditCardCompany issuer,
            Economic holder,
            double limit
    ) {
        this.uniqueId = UUID.randomUUID();
        this.issuer = issuer;
        this.holder = holder;
        this.currency= issuer.getPreferredCurrency();

        this.expiration = DateUtils.addDays(new Date(), 90);

        this.limit = limit;
        this.balance = 0d;
        this.active = false;
    }

    private final UUID uniqueId;
    private final CardIssuer issuer;
    private final Economic holder;
    private final String currency;

    private final Date expiration;

    private final double limit;
    private double balance;

    private boolean active;

    @Override
    public CardIssuer getIssuer() {
        return issuer;
    }

    @Override
    public Economic getHolder() {
        return holder;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public double getPayable() {
        return Math.max(limit - balance, 0);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void addBalance(double delta) {
        balance += delta;
    }

    @Override
    public void removeBalance(double delta) {
        balance -= delta;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    // IO
    public CreditCardData toData() {
        CreditCardData data = new CreditCardData();

        data.uniqueId = uniqueId;
        data.issuer = issuer.getUniqueId();
        data.holder = holder.getUniqueId();
        data.currency = currency;
        data.limit = limit;
        data.balance = balance;
        data.expiration = expiration;
        data.active = active;

        return data;
    }

    public static CreditCard fromData(CreditCardData data, LedgerState state) {
        return new CreditCard(
                data.uniqueId,
                state.getCardIssuer(data.issuer),
                state.getAssetholder(data.holder),
                data.currency,
                data.limit,
                data.balance,
                data.expiration,
                data.active
        );
    }

    private CreditCard(
            UUID uniqueId,
            CardIssuer issuer,
            Economic holder,
            String currency,
            double limit,
            double balance,
            Date expiration,
            boolean active
    ) {
        this.uniqueId = uniqueId;
        this.issuer = issuer;
        this.holder = holder;
        this.currency = currency;
        this.limit = limit;
        this.balance = balance;
        this.expiration = expiration;
        this.active = active;
    }

}
