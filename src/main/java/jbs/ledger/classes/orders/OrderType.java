package jbs.ledger.classes.orders;

public enum OrderType {
    BUY_LIMIT,
    BUY_MARKET,

    SELL_LIMIT,
    SELL_MARKET;

    public boolean isBuy() {
        switch(this) {
            case BUY_LIMIT:
            case BUY_MARKET:
                return true;
        }

        return false;
    }

    public boolean isMarket() {
        switch(this) {
            case BUY_MARKET:
            case SELL_MARKET:
                return true;
        }

        return false;
    }
}
