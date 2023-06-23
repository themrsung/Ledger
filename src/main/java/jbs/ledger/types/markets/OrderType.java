package jbs.ledger.types.markets;

/**
 * Type of order.
 */
public enum OrderType {
    /**
     * Order will be fulfilled only when price is lower or equal to than specified.
     */
    BUY_LIMIT,

    /**
     * Order will be fulfilled at any price.
     */
    BUY_MARKET,

    /**
     * Order will be fulfilled only when price is higher or equal to than specified.
     */
    SELL_LIMIT,

    /**
     * Order will be fulfilled at any price.
     */
    SELL_MARKET;

    boolean isBuy() {
        switch (this) {
            case BUY_LIMIT:
            case BUY_MARKET:
                return true;
        }

        return false;
    }

    boolean isMarket() {
        switch (this) {
            case BUY_MARKET:
            case SELL_MARKET:
                return true;
        }

        return false;
    }
}
