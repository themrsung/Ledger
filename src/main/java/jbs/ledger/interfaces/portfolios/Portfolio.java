package jbs.ledger.interfaces.portfolios;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * A portfolio of assets.
 * @param <A> Type of asset to hold.
 */
public interface Portfolio<A extends Asset> {
    ArrayList<A> get();
    @Nullable
    A get(String symbol);

    void add(A asset);
    void remove(A asset);

    default void add(Portfolio<A> portfolio) {
        for (A asset : portfolio.get()) {
            add(asset);
        }
    }

    default void remove(Portfolio<A> portfolio) {
        for (A asset : portfolio.get()) {
            remove(asset);
        }
    }

    default boolean contains(A asset) {
        return get().contains(asset);
    }

    default boolean contains(Portfolio<A> portfolio) {
        for (A asset : portfolio.get()) {
            if (!contains(asset)) {
                return false;
            }
        }

        return true;
    }

    void clean();
}
