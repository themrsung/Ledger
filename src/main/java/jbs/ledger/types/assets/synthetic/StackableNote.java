package jbs.ledger.types.assets.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Stackable Note
 * A fungible note.
 * @param <D> Type of asset to deliver.
 */
public final class StackableNote<D extends Asset> extends Note<D> {
    /**
     * Issues a stackable note
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     * @param quantity How many notes are issued (NOT the quantity of underlying to be delivered)
     */
    public StackableNote(
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration,
            long quantity
    ) {
        super(symbol, delivery, deliverer, expiration, quantity);
    }

    public StackableNote(StackableNote<D> copy) {
        super(copy);
    }
    public StackableNote() {
        super();
    }

    @Override
    public StackableNote<D> copy() {
        return new StackableNote<>(this);
    }

    @Override
    public StackableNote<D> negate() {
        StackableNote<D> copy = copy();

        copy.setQuantity(getQuantity() * -1);

        return copy;
    }
}
