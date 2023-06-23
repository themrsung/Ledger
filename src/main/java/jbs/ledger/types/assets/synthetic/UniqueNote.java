package jbs.ledger.types.assets.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Unique;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

/**
 * Unique note
 * A non-fungible note. Can be used to issue contracts.
 * @param <D> Type of asset to deliver.
 */
public final class UniqueNote<D extends Asset> extends Note<D> implements Unique {
    /**
     * Creates 1 unique note
     * @param uniqueId Unique ID of this particular note
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     */
    public UniqueNote(
            UUID uniqueId,
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration
    ) {
        super(symbol, delivery, deliverer, expiration, 1);

        this.uniqueId = uniqueId;
    }

    public UniqueNote(final UniqueNote<D> copy) {
        super(copy);
        uniqueId = copy.uniqueId;
    }
    public UniqueNote() {
        super();
        uniqueId = null;
    }
    private final UUID uniqueId;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public boolean isStackable(Asset asset) {
        return false;
    }

    @Override
    public UniqueNote<D> copy() {
        return new UniqueNote<>(this);
    }

    @Override
    public UniqueNote<D> negate() {
        UniqueNote<D> copy = copy();

        copy.setQuantity(getQuantity() * -1);

        return copy;
    }
}
