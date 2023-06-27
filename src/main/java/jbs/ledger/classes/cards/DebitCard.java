package jbs.ledger.classes.cards;

import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.cards.CardIssuer;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.io.types.cards.DebitCardData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.UUID;

public final class DebitCard implements Card {
    public DebitCard(
            Bank issuer,
            Economic holder,
            Account<Cash> account
    ) {
        this.uniqueId = UUID.randomUUID();
        this.issuer = issuer;
        this.holder = holder;
        this.currency = issuer.getPreferredCurrency();

        this.expiration = DateUtils.addDays(new Date(), 10000);

        this.linkedAccount = account;
        this.active = false;
    }
    private final UUID uniqueId;
    private final CardIssuer issuer;
    private final Economic holder;
    private final String currency;

    private boolean active;

    private final Date expiration;

    private final Account<Cash> linkedAccount;

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
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public double getPayable() {
        return linkedAccount.getContent().getBalance();
    }

    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public void addBalance(double delta) {
        linkedAccount.getContent().removeBalance(delta);
    }

    @Override
    public void removeBalance(double delta) {
        linkedAccount.getContent().addBalance(delta);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    // IO
    public DebitCardData toData() {
        DebitCardData data = new DebitCardData();

        data.uniqueId = uniqueId;
        data.issuer = issuer.getUniqueId();
        data.holder = holder.getUniqueId();
        data.currency = currency;
        data.linkedAccount = linkedAccount.getUniqueId();
        data.expiration = expiration;
        data.active = active;

        return data;
    }

    public static DebitCard fromData(DebitCardData data, LedgerState state) {
        return new DebitCard(
                data.uniqueId,
                state.getCardIssuer(data.issuer),
                state.getAssetholder(data.holder),
                data.currency,
                state.getBankAccount(data.linkedAccount),
                data.expiration,
                data.active
        );
    }

    private DebitCard(
            UUID uniqueId,
            CardIssuer issuer,
            Economic holder,
            String currency,
            Account<Cash> linkedAccount,
            Date expiration,
            boolean active
    ) {
        this.uniqueId = uniqueId;
        this.issuer = issuer;
        this.holder = holder;
        this.currency = currency;
        this.linkedAccount = linkedAccount;
        this.expiration = expiration;
        this.active = active;
    }


}
