package jbs.ledger.types.assets.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.AbstractNoteData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.assets.synthetic.unique.BondForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.AssetType;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;

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
     * Creates unique note(s)
     * @param uniqueId Unique ID of this particular note
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     * @param quantity How many contracts to create
     */
    public UniqueNote(
            UUID uniqueId,
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration,
            long quantity
    ) {
        super(symbol, delivery, deliverer, expiration, quantity);

        this.uniqueId = uniqueId;
    }

    public UniqueNote(final UniqueNote<D> copy) {
        super(copy);
        uniqueId = copy.uniqueId;
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

    @Override
    public AssetType getType() {
        switch (getDelivery().getType()) {
            case CASH:
                return AssetType.NOTE;
            case COMMODITY:
                return AssetType.COMMODITY_FORWARD;
            case STOCK:
                return AssetType.STOCK_FORWARD;
        }

        return null;
    }

    // Offset
    public boolean isOffsetable(UniqueNote<D> note) {
        return getUniqueId().equals(note.getUniqueId());
    }

    // IO
    public static UniqueNote<Cash> fromData(NoteData data, LedgerState state) {
        return new UniqueNote<>(
                data.uniqueId,
                data.symbol,
                Cash.fromData((CashData) data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity
        );
    }

    public static UniqueNote<Commodity> fromData(CommodityForwardData data, LedgerState state) {
        return new UniqueNote<>(
                data.uniqueId,
                data.symbol,
                Commodity.fromData((CommodityData) data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity
        );
    }

    public static UniqueNote<Stock> fromData(StockForwardData data, LedgerState state) {
        return new UniqueNote<>(
                data.uniqueId,
                data.symbol,
                Stock.fromData((StockData) data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity
        );
    }

    public AbstractNoteData toData() {
        if (getDelivery() instanceof Cash) {
            NoteData data = new NoteData();
            data.uniqueId = getUniqueId();
            data.symbol = getSymbol();

            data.delivery = ((Cash) getDelivery()).toData();

            data.type = getType();
            data.deliverer = getDeliverer().getUniqueId();
            data.expiration = getExpiration();
            data.quantity = getQuantity();

            return data;
        } else if (getDelivery() instanceof Commodity) {
            CommodityForwardData data = new CommodityForwardData();
            data.uniqueId = getUniqueId();
            data.symbol = getSymbol();

            data.delivery = ((Commodity) getDelivery()).toData();

            data.type = getType();
            data.deliverer = getDeliverer().getUniqueId();
            data.expiration = getExpiration();
            data.quantity = getQuantity();

            return data;
        } else if (getDelivery() instanceof Stock) {
            StockForwardData data = new StockForwardData();
            data.uniqueId = getUniqueId();
            data.symbol = getSymbol();

            data.delivery = ((Stock) getDelivery()).toData();

            data.type = getType();
            data.deliverer = getDeliverer().getUniqueId();
            data.expiration = getExpiration();
            data.quantity = getQuantity();

            return data;
        }

        return null;
    }
}
