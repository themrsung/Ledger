package jbs.ledger.interfaces.conditions;

import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.conditions.PriceConditionType;

public interface PriceCondition<A> extends Condition {
    Market<A> getMarket();
    A getAsset();
    PriceConditionType getType();
    double getPrice();

    @Override
    default boolean isMet() {
        double p1 = getPrice();
        double p2 = getMarket().getUnitPrice(getAsset());

        switch (getType()) {
            case EQUALS:
                return p1 == p2;
            case HIGHER_THAN:
                return p1 > p2;
            case HIGHER_THAN_OR_EQUALS_TO:
                return p1 >= p2;
            case LOWER_THAN:
                return p1 < p2;
            case LOWER_THAN_OR_EQUALS_TO:
                return p1 <= p2;
        }

        return false;
    }

    @Override
    Condition invert();
}
