package jbs.ledger.types.conditions;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.conditions.Condition;
import jbs.ledger.interfaces.conditions.PriceCondition;
import jbs.ledger.interfaces.markets.Market;

public class OptionExerciseCondition<A extends Asset> implements PriceCondition<A> {
    public OptionExerciseCondition(
            Market<A> market,
            A asset,
            PriceConditionType type,
            double price
    ) {
        this.market = market;
        this.asset = asset;
        this.type = type;
        this.price = price;
    }

    public OptionExerciseCondition(OptionExerciseCondition<A> copy) {
        this.market = copy.market;
        this.asset = copy.asset;
        this.type = copy.type;
        this.price = copy.price;
    }
    public OptionExerciseCondition() {
        this.market = null;
        this.asset = null;
        this.type = null;
        this.price = 0d;
    }

    private final Market<A> market;
    private final A asset;
    private final PriceConditionType type;
    private final double price;

    @Override
    public Market<A> getMarket() {
        return market;
    }

    @Override
    public A getAsset() {
        return asset;
    }

    @Override
    public PriceConditionType getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Condition invert() {
        assert type != null;

        return new OptionExerciseCondition<A>(
                market,
                asset,
                type.invert(),
                price
        );
    }
}
