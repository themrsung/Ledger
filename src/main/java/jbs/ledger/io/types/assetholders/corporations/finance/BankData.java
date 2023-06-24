package jbs.ledger.io.types.assetholders.corporations.finance;

import jbs.ledger.io.types.assetholders.corporations.CorporationData;
import jbs.ledger.io.types.banking.BankAccountData;

import java.util.ArrayList;

public final class BankData extends CorporationData {
    public BankData(CorporationData parent) {
        super(parent);
    }

    public BankData(BankData copy) {
        super(copy);

        this.accounts = copy.accounts;
        this.interestRate = copy.interestRate;
    }

    public BankData() {
        super();
    }

    public ArrayList<BankAccountData> accounts = new ArrayList<>();
    public float interestRate;
}
