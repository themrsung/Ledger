package jbs.ledger.types.assets.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.conditions.Condition;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Conditional Note
 * Only gets exercised when condition is met. Usually used for options.
 * @param <D> Type of delivery.
 */
public final class ConditionalNote<D extends Asset> extends Note<D> {
    /**
     * Issues a conditional note (aka Option)
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     * @param quantity How many notes are issued (NOT the quantity of underlying to be delivered)
     * @param condition Condition required for this note to be delivered
     */
    public ConditionalNote(
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration,
            long quantity,
            Condition condition
    ) {
       super(symbol, delivery, deliverer, expiration, quantity);

       this.condition = condition;
    }

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
