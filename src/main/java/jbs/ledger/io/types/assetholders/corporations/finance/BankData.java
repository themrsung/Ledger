package jbs.ledger.io.types.assetholders.corporations.finance;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.banking.BankAccountData;
import jbs.ledger.io.types.cards.DebitCardData;

import java.util.ArrayList;

public final class BankData extends CorporationData {
    public BankData(CorporationData parent) {
        super(parent);
    }

    public BankData(BankData copy) {
        super(copy);

        this.accounts = copy.accounts;
        this.interestRate = copy.interestRate;
        this.issuedCards = copy.issuedCards;
    }

    public BankData() {
        super();
    }

    public ArrayList<BankAccountData> accounts = new ArrayList<>();
    public float interestRate;

    public ArrayList<DebitCardData> issuedCards = new ArrayList<>();
}
