package jbs.ledger.types.assets;

import jbs.ledger.interfaces.conditions.Condition;

/**
 * Conditional Note
 * Only gets exercised when condition is met. Usually used for options.
 * @param <D> Type of delivery.
 */
public final class ConditionalNote<D> extends Note<D> {
    public ConditionalNote(ConditionalNote<D> copy) {
        super(copy);

        this.condition = copy.condition;
    }
    public ConditionalNote() {
        super();

        condition = null;
    }

    private final Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public ConditionalNote<D> copy() {
        return new ConditionalNote<>(this);
    }

    @Override
    public ConditionalNote<D> negate() {
        ConditionalNote<D> copy = copy();

        copy.setQuantity(getQuantity() * -1);

        return copy;
    }
}
