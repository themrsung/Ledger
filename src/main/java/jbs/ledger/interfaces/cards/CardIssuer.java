package jbs.ledger.interfaces.cards;

import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Symbolic;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public interface CardIssuer extends Economic, Symbolic {
    ArrayList<Card> getIssuedCards();

    void addIssuedCard(Card card);
    boolean removeIssuedCard(Card card);

    default ArrayList<Card> getIssuedCards(Economic holder) {
        ArrayList<Card> cards = new ArrayList<>();

        for (Card c : getIssuedCards()) {
            if (c.getHolder().equals(holder)) {
                cards.add(c);
            }
        }

        return cards;
    }

    @Nullable
    default Card getIssuedCard(UUID uniqueId) {
        for (Card c : getIssuedCards()) {
            if (c.getUniqueId().equals(uniqueId)) {
                return c;
            }
        }

        return null;
    }

}
