package jbs.ledger.commands.economy.cards;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.assetholders.corporations.finance.CreditCardCompany;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.cards.CardIssuer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class CardsCommandCompleter extends LedgerCommandAutoCompleter {
    public CardsCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public CardsCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.ISSUE);
            list.addAll(LedgerCommandKeywords.BALANCE);
            list.addAll(LedgerCommandKeywords.CANCEL);
            list.addAll(LedgerCommandKeywords.ACTIVATE);
        } else if (args.length < 3) {
            String action = args[1].toLowerCase();

            if (LedgerCommandKeywords.ISSUE.contains(action)) {
                if (getActor() instanceof Bank || getActor() instanceof CreditCardCompany) {
                    for (Assetholder a : getState().getAssetholders()) {
                        list.add(a.getSearchTag());
                    }
                }
            } else if (LedgerCommandKeywords.BALANCE.contains(action) || LedgerCommandKeywords.CANCEL.contains(action)) {
                for (Card c : getActor().getCards(getState())) {
                    list.add(c.getSearchTag());
                }

                if (LedgerCommandKeywords.CANCEL.contains(action)) {
                    if (getActor() instanceof CardIssuer) {
                        CardIssuer issuer = (CardIssuer) getActor();

                        for (Card c : issuer.getIssuedCards()) {
                            list.add(c.getSearchTag());
                        }
                    }
                }
            }
        }

        return list;
    }
}
