package jbs.ledger.interfaces.assets;

public interface IntegralAsset extends Asset {
    long getQuantity();

    void setQuantity(long quantity);
    default void addQuantity(long delta) {
        setQuantity(getQuantity() + delta);
    }
    default void removeQuantity(long delta) {
        setQuantity(getQuantity() - delta);
    }

    @Override
    IntegralAsset copy();

    default IntegralAsset negate() {
        IntegralAsset copy = copy();
        copy.setQuantity(getQuantity() * -1);

        return copy;
    }
}
