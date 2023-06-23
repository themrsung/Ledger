package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.Asset;

/**
 * Stackable Note
 * A fungible note.
 * @param <D> Type of asset to deliver.
 */
public final class StackableNote<D extends Asset> extends Note<D> {
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
