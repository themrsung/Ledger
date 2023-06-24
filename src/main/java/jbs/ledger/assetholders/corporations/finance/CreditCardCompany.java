package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.io.types.assetholders.corporations.finance.BankData;
import jbs.ledger.io.types.assetholders.corporations.finance.CreditCardCompanyData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

/**
 * Credit card companies can issue credit cards
 */
public final class CreditCardCompany extends Corporation {
    public CreditCardCompany(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);
    }

    public CreditCardCompany(CreditCardCompany copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.CREDIT_CARD_COMPANY;
    }


    // IO

    @Override
    public CreditCardCompanyData toData() {
        CreditCardCompanyData data = new CreditCardCompanyData(super.toData());

        return data;
    }

    public static CreditCardCompany getEmptyInstance(UUID uniqueId) {
        return new CreditCardCompany(uniqueId);
    }

    private CreditCardCompany(UUID uniqueId) {
        super(uniqueId);

    }

    public void load(BankData data, LedgerState state) {
        super.load(data, state);
    }
}
