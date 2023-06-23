package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Unique;

import java.util.UUID;

/**
 * Unique note
 * A non-fungible note. Can be used to issue contracts.
 * @param <D> Type of asset to deliver.
 */
public final class UniqueNote<D extends Asset> extends Note<D> implements Unique {
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
