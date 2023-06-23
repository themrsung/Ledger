package jbs.ledger.types.conditions;

public enum PriceConditionType {
    /**
     * For call options
     */
    HIGHER_THAN_OR_EQUALS_TO,

    /**
     * For put options
     */
    LOWER_THAN_OR_EQUALS_TO,

    // Other

    HIGHER_THAN,
    LOWER_THAN,
    EQUALS,
    ALWAYS,
    NEVER;

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
            case ALWAYS:
                return NEVER;
            case NEVER:
                return ALWAYS;
        }

        return null;
    }
}
