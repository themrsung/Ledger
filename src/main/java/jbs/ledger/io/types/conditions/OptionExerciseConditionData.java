package jbs.ledger.io.types.conditions;

import jbs.ledger.io.types.assets.AssetData;
import jbs.ledger.types.conditions.PriceConditionType;

import java.util.UUID;

public final class OptionExerciseConditionData {
    public OptionExerciseConditionData() {}

    public UUID market;
    public AssetData asset;
    public PriceConditionType type;
    public double price;
}
