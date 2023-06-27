package jbs.ledger.timers.economy;

import jbs.ledger.Ledger;
import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.cards.CardIssuer;
import jbs.ledger.types.assets.basic.Cash;
import org.bukkit.Bukkit;

import java.util.Date;

public class CardExpirationChecker implements Runnable {
    public CardExpirationChecker(Ledger ledger) {
        this.ledger = ledger;
    }
    private final Ledger ledger;

    @Override
    public void run() {
        for (CardIssuer ci : ledger.getState().getCardIssuers()) {
            for (Card c : ci.getIssuedCards()) {
                if (c.getExpiration().before(new Date())) {
                    double remainingBalance = c.getBalance();

                    if (remainingBalance > 0d) {
                        Cash bal = new Cash(c.getCurrency(), remainingBalance);
                        Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                                c.getHolder(),
                                ci,
                                bal,
                                AssetTransferCause.CREDIT_CARD_EXPIRED
                        ));
                    }

                    ci.removeIssuedCard(c);
                }
            }
        }
    }
}
