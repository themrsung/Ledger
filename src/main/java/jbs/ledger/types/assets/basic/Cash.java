package jbs.ledger.types.assets.basic;

import jbs.ledger.interfaces.assets.FractionalAsset;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.AssetType;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Cash
 * Has a currency code(string) and balance(double).
 */
public final class Cash implements FractionalAsset {
    /**
     * Constructs a new cash instance
     * @param currency Currency code
     * @param balance Amount
     */
    public Cash(
            String currency,
            double balance
    ) {
        this.symbol = currency;
        this.balance = balance;
    }

    public Cash(Cash copy) {
        this.symbol = copy.symbol;
        this.balance = copy.balance;
    }
    public Cash() {
        this.symbol = null;
        this.balance = 0d;
    }

    private final String symbol;
    private double balance;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public Cash copy() {
        return new Cash(this);
    }

    @Override
    public Cash negate() {
        return new Cash(symbol, -balance);
    }

    @Override
    public AssetType getType() {
        return AssetType.CASH;
    }

    // IO
    public static Cash fromData(CashData data) {
        return new Cash(data);
    }

    private Cash(CashData data) {
        this.symbol = data.symbol;
        this.balance = data.balance;
    }

    public CashData toData() {
        CashData data = new CashData();

        data.type = AssetType.CASH;
        data.symbol = symbol;
        data.balance = balance;

        return data;
    }

    // Input
    public static Cash fromInput(String input, LedgerState state) {
        String fallbackCurrency = state.getConfig().DEFAULT_CURRENCY;

        // Check for currency input
        String currency = null;

        for (String c : state.getCurrencies()) {
            if (input.toUpperCase().startsWith(c)) {
                currency = c;
                break;
            }
        }

        if (currency == null) {
            currency = fallbackCurrency;
        }

        // Check for unit input
        String numberWithUnit = input.toUpperCase().replaceAll(currency, "");

        final List<String> koreanDelimiters = Arrays.asList("경", "조", "억", "만", "천", "백", "십");
        final List<Double> koreanUnits = Arrays.asList(10000000000000000d, 1000000000000d, 100000000d, 10000d, 1000d, 100d, 10d);

        for (int i = 0; i < koreanDelimiters.size(); i++) {
            String d = koreanDelimiters.get(i);
            if (numberWithUnit.contains(d)) {
                String numberOnly = numberWithUnit.replaceAll(d, "");
                try {
                    return new Cash(currency, Double.parseDouble(numberOnly) * koreanUnits.get(i));
                } catch (NumberFormatException e) {
                    return new Cash(currency, 0);
                }
            }
        }

        final List<String> englishDelimiters = Arrays.asList("q", "t", "b", "m", "k");
        final List<Double> englishUnits = Arrays.asList(1000000000000000d, 1000000000000d, 1000000000d, 1000000d, 1000d);

        for (int i = 0; i < englishDelimiters.size(); i++) {
            String d = englishDelimiters.get(i);
            if (numberWithUnit.contains(d)) {
                String numberOnly = numberWithUnit.replaceAll(d, "");
                try {
                    return new Cash(currency, Double.parseDouble(numberOnly) * englishUnits.get(i));
                } catch (NumberFormatException e) {
                    return new Cash(currency, 0);
                }
            }
        }

        try {
            return new Cash(currency, Double.parseDouble(numberWithUnit));
        } catch (NumberFormatException e) {
            return new Cash(currency, 0);
        }
    }

    public String format() {
        return getSymbol() + " " + NumberFormat.getInstance().format(getBalance());
    }

}
