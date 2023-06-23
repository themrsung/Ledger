package jbs.ledger.types.assets;

public enum AssetType {
    // BASIC

    CASH,
    COMMODITY,
    STOCK,

    // UNIQUE NOTES

    NOTE,
    COMMODITY_FORWARD,
    STOCK_FORWARD,

    // STACKABLE_NOTES

    BOND,
    COMMODITY_FUTURES,
    STOCK_FUTURES,

    // CONDITIONAL_NOTES

    FOREX_OPTION,
    COMMODITY_OPTION,
    STOCK_OPTION;
}
