package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.cards.CreditCard;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.cards.CardIssuer;
import jbs.ledger.io.types.assetholders.corporations.finance.BankData;
import jbs.ledger.io.types.assetholders.corporations.finance.CreditCardCompanyData;
import jbs.ledger.io.types.cards.CreditCardData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Credit card companies can issue credit cards
 */
public final class CreditCardCompany extends Corporation implements CardIssuer {
    public CreditCardCompany(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital,
            long shareCount
    ) {
        super(uniqueId, name, symbol, currency, capital, shareCount);

        this.issuedCards = new ArrayList<>();
    }

    public CreditCardCompany(CreditCardCompany copy) {
        super(copy);
        this.issuedCards = copy.issuedCards;
    }

    private ArrayList<CreditCard> issuedCards;


    // Cards

    @Override
    public ArrayList<Card> getIssuedCards() {
        return new ArrayList<>(issuedCards);
    }

    @Override
    public void addIssuedCard(Card card) {
        if (card instanceof CreditCard) {
            issuedCards.add((CreditCard) card);
        }
    }

    @Override
    public boolean removeIssuedCard(Card card) {
        if (card instanceof CreditCard) {
            return issuedCards.remove((CreditCard) card);
        }

        return false;
    }


    @Override
    public AssetholderType getType() {
        return AssetholderType.CREDIT_CARD_COMPANY;
    }

    // IO

    @Override
    public CreditCardCompanyData toData() {
        CreditCardCompanyData data = new CreditCardCompanyData(super.toData());

        for (CreditCard card : issuedCards) {
            data.issuedCards.add(card.toData());
        }

        return data;
    }

    public static CreditCardCompany getEmptyInstance(UUID uniqueId) {
        return new CreditCardCompany(uniqueId);
    }

    private CreditCardCompany(UUID uniqueId) {
        super(uniqueId);

        this.issuedCards = new ArrayList<>();
    }

    public void load(CreditCardCompanyData data, LedgerState state) {
        super.load(data, state);

        this.issuedCards.clear();

        for (CreditCardData ccd : data.issuedCards) {
            this.issuedCards.add(CreditCard.fromData(ccd, state));
        }
    }
}
