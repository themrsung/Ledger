package jbs.ledger.types.assets.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.AbstractNoteData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.AssetType;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Option
 * A note that is only delivered when the price of a certain asset is within the parameters.
 * @param <D> Type of asset to deliver.
 */
public final class Option<D extends Asset> extends Note<D> {
    /**
     * Issues a stackable note
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     * @param quantity How many notes are issued (NOT the quantity of underlying to be delivered)
     */
    public Option(
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration,
            long quantity,
            double exercisePrice,
            Market<D> market
    ) {
        super(symbol, delivery, deliverer, expiration, quantity);

        this.exercisePrice = exercisePrice;
        this.market = market;
    }

    public Option(Option<D> copy) {
        super(copy);

        this.exercisePrice = copy.exercisePrice;
        this.market = copy.market;
    }

    private final double exercisePrice;
    private final Market<D> market;

    public double getExercisePrice() {
        return exercisePrice;
    }

    public Market<D> getMarket() {
        return market;
    }

    @Override
    public Option<D> copy() {
        return new Option<>(this);
    }

    @Override
    public Option<D> negate() {
        Option<D> copy = copy();

        copy.setQuantity(getQuantity() * -1);

        return copy;
    }

    @Override
    public AssetType getType() {
        switch (getDelivery().getType()) {
            case STOCK:
                return AssetType.STOCK_OPTION;
        }

        return null;
    }

    // IO
    public static Option<Cash> fromData(BondData data, LedgerState state) {
        return new Option<?>(
                data.symbol,
                Cash.fromData((CashData) data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity
        );
    }

    public static Option<Commodity> fromData(CommodityFuturesData data, LedgerState state) {
        return new Option<?>(
                    data.symbol,
                    Commodity.fromData((CommodityData) data.delivery),
                    state.getAssetholder(data.deliverer),
                    data.expiration,
                    data.quantity
            );
    }

    public static Option<Stock> fromData(StockFuturesData data, LedgerState state) {
        return new Option<?>(
                data.symbol,
                Stock.fromData((StockData) data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity
        );
    }

    public AbstractNoteData toData() {
        if (getDelivery() instanceof Cash) {
            BondData data = new BondData();
            data.symbol = getSymbol();

            data.delivery = ((Cash) getDelivery()).toData();

            data.type = getType();
            data.deliverer = getDeliverer().getUniqueId();
            data.expiration = getExpiration();
            data.quantity = getQuantity();

            return data;
        } else if (getDelivery() instanceof Commodity) {
            CommodityFuturesData data = new CommodityFuturesData();
            data.symbol = getSymbol();

            data.delivery = ((Commodity) getDelivery()).toData();

            data.type = getType();
            data.deliverer = getDeliverer().getUniqueId();
            data.expiration = getExpiration();
            data.quantity = getQuantity();

            return data;
        } else if (getDelivery() instanceof Stock) {
            StockFuturesData data = new StockFuturesData();
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
