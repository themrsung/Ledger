package jbs.ledger.classes.markets.synthetic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.synthetic.CommodityFuturesOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.synthetic.CommodityFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class CommodityFuturesMarket extends AbstractMarket<StackableNote<Commodity>> {
    public CommodityFuturesMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            StackableNote<Commodity> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public CommodityFuturesMarket(AbstractMarket<StackableNote<Commodity>> copy) {
        super(copy);
    }

    @Override
    public StackableNote<Commodity> getUnitAsset() {
        return unitAsset.copy();
    }

    // IO
    public MarketData<CommodityFuturesData, CommodityFuturesOrderData> toData() {
        MarketData<CommodityFuturesData, CommodityFuturesOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = (CommodityFuturesData) ((StackableNote<Commodity>) getUnitAsset()).toData();

        for (Order<StackableNote<Commodity>> o : getOrders()) {
            CommodityFuturesOrder co = (CommodityFuturesOrder) o;
            data.orders.add(co.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static CommodityFuturesMarket fromData(MarketData<CommodityFuturesData, CommodityFuturesOrderData> data, LedgerState state) {
        CommodityFuturesMarket m = new CommodityFuturesMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                StackableNote.fromData(data.unitAsset, state),
                data.tickSize
        );

        m.setPrice(data.price);

        for (CommodityFuturesOrderData od : data.orders) {
            m.placeOrder(CommodityFuturesOrder.fromData(od, state));
        }

        return m;
    }
}
