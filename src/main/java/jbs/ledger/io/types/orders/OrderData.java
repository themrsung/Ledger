package jbs.ledger.io.types.orders;

import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.io.types.assets.synthetic.AbstractNoteData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

/**
 * Data class for Order
 * @param <C> Type of collateral (NOT the underlying asset)
 */
public class OrderData<C extends AbstractNoteData> {
    public OrderData(OrderData<?> copy) {
        this.uniqueId = copy.uniqueId;
        this.type = copy.type;
        this.sender = copy.sender;
        this.date = copy.date;
        this.price = copy.price;
        this.quantity = copy.quantity;
        this.cashCollateral = copy.cashCollateral;
        this.assetCollateral = null;
    }

    public OrderData() {}

    public UUID uniqueId;
    public OrderType type;
    public UUID sender;
    public Date date;
    public double price;
    public long quantity;

    @Nullable
    public NoteData cashCollateral;
    @Nullable
    public C assetCollateral;
}
