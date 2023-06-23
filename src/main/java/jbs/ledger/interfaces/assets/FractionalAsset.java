package jbs.ledger.interfaces.assets;


/**
 * An asset that supports decimal places.
 */
public interface FractionalAsset extends Asset {
    double getBalance();

    void setBalance(double balance);
    default void addBalance(double balance) {
        setBalance(getBalance() + balance);
    }
    default void removeBalance(double balance) {
        setBalance(getBalance() - balance);
    }

    FractionalAsset copy();

    default FractionalAsset negate() {
        FractionalAsset copy = copy();
        copy.setBalance(getBalance() * -1);

        return copy;
    }

    @Override
    default FractionalAsset multiply(double modifier) {
        FractionalAsset copy = copy();
        copy.setBalance(getBalance() * modifier);

        return copy;
    }
}
