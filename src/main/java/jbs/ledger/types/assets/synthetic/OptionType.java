package jbs.ledger.types.assets.synthetic;

import java.util.Date;

public enum OptionType {
    AMERICAN_CALL,
    AMERICAN_PUT,
    EUROPEAN_CALL,
    EUROPEAN_PUT;

    public boolean isCall() {
        switch (this) {
            case AMERICAN_CALL:
            case EUROPEAN_CALL:
                return true;
        }

        return false;
    }

    public boolean isAmerican() {
        switch (this) {
            case AMERICAN_CALL:
            case AMERICAN_PUT:
                return true;
        }

        return false;
    }

    public boolean isEuropean() {
        switch (this) {
            case EUROPEAN_CALL:
            case EUROPEAN_PUT:
                return true;
        }

        return false;
    }

    public boolean canBeExercised(double marketPrice, double exercisePrice, Date date) {
        boolean priceCondition = isCall() ? marketPrice >= exercisePrice : marketPrice <= exercisePrice;
        boolean dateCondition = isAmerican() || date.before(new Date());

        return priceCondition && dateCondition;
    }
}
