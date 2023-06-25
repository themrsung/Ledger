package jbs.ledger.types.markets;

public final class MarketTickData {
    public MarketTickData(
            double price,
            long quantity
    ) {
        this.price = price;
        this.quantity = quantity;
    }

    private final double price;
    private final long quantity;

    public double getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
