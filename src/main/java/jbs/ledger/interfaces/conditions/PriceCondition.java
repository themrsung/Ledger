package jbs.ledger.interfaces.conditions;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.conditions.PriceConditionType;

/**
 * A condition that is dependent on the fair value of an asset.
 * @param <A> The asset to check the price of.
 */
public interface PriceCondition<A extends Asset> extends Condition {
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
