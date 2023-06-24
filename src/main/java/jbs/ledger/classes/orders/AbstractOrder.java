package jbs.ledger.classes.orders;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.orders.OrderData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractOrder<A extends Asset> implements Order<A> {
    public AbstractOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity,
            @Nullable UniqueNote<Cash> cashCollateral,
            @Nullable UniqueNote<A> assetCollateral
    ) {
        this.uniqueId = uniqueId;
        this.type = type;
        this.sender = sender;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.cashCollateral = cashCollateral;
        this.assetCollateral = assetCollateral;
    }

    public AbstractOrder(AbstractOrder<A> copy) {
        this.uniqueId = copy.uniqueId;
        this.type = copy.type;
        this.sender = copy.sender;
        this.date = copy.date;
        this.price = copy.price;
        this.quantity = copy.quantity;
        this.cashCollateral = copy.cashCollateral;
        this.assetCollateral = copy.assetCollateral;
    }

    private final UUID uniqueId;
    private final OrderType type;
    private final Economic sender;
    private final Date date;

    private double price;
    private long quantity;

    @Nullable
    private final UniqueNote<Cash> cashCollateral;
    @Nullable
    private final UniqueNote<A> assetCollateral;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public OrderType getType() {
        return type;
    }

    @Override
    public Economic getSender() {
        return sender;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    @Nullable
    public UniqueNote<Cash> getCashCollateral() {
        return cashCollateral;
    }

    @Override
    @Nullable
    public UniqueNote<A> getAssetCollateral() {
        return assetCollateral;
    }

    // IO
    public OrderData<?> toData() {
        OrderData<?> data = new OrderData<>();

        data.uniqueId = uniqueId;
        data.type = type;
        data.sender = sender.getUniqueId();
        data.date = date;
        data.price = price;
        data.quantity = quantity;

        if (cashCollateral != null) data.cashCollateral = (NoteData) cashCollateral.toData();

        return data;
    }
}
