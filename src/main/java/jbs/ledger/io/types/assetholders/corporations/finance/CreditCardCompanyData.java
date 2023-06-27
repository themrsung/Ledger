package jbs.ledger.io.types.assetholders.corporations.finance;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.cards.CreditCardData;

import java.util.ArrayList;

public final class CreditCardCompanyData extends CorporationData {
    public CreditCardCompanyData(CorporationData parent) {
        super(parent);
    }

    public CreditCardCompanyData(CreditCardCompanyData copy) {
        super(copy);

        this.issuedCards = copy.issuedCards;

    }

    public CreditCardCompanyData() {
        super();
    }

    public ArrayList<CreditCardData> issuedCards = new ArrayList<>();

}
