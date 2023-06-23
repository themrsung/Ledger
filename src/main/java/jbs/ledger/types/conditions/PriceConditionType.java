package jbs.ledger.types.conditions;

public enum PriceConditionType {
    EQUALS,
    HIGHER_THAN_OR_EQUALS_TO,
    HIGHER_THAN,
    LOWER_THAN_OR_EQUALS_TO,
    LOWER_THAN;

    PriceConditionType invert() {
        switch(this) {
            case EQUALS:
                return EQUALS;
            case HIGHER_THAN_OR_EQUALS_TO: // This is not a mistake. These conditions are used for options tracing.
                return LOWER_THAN_OR_EQUALS_TO;
            case LOWER_THAN_OR_EQUALS_TO:
                return HIGHER_THAN_OR_EQUALS_TO;
            case HIGHER_THAN:
                return LOWER_THAN;
            case LOWER_THAN:
                return HIGHER_THAN;
        }

        return null;
    }
}
