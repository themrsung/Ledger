package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.assets.Delayed;
import jbs.ledger.interfaces.assets.IntegralAsset;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;
import java.util.Date;

public abstract class Note<D extends Asset> implements IntegralAsset, Delayed<D> {
    public Note(final Note<D> copy) {
        this.symbol = copy.symbol;
        this.delivery = copy.delivery;
        this.deliverer = copy.deliverer;
        this.expiration = copy.expiration;
        this.quantity = copy.quantity;
    }
    public Note() {
        this.symbol = null;
        this.delivery = null;
        this.deliverer = null;
        this.expiration = null;
        this.quantity = 0;
    }

    private final String symbol;
    private final D delivery;
    private final Economic deliverer;
    @Nullable
    private final Date expiration;
    private long quantity;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public D getDelivery() {
        return delivery;
    }

    @Override
    @Nullable
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public Economic getDeliverer() {
        return deliverer;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
