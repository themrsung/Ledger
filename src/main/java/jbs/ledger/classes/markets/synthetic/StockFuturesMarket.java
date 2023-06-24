package jbs.ledger.classes.markets.synthetic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.synthetic.StockFuturesOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class StockFuturesMarket extends AbstractMarket<StackableNote<Stock>> {
    public StockFuturesMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            StackableNote<Stock> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public StockFuturesMarket(AbstractMarket<StackableNote<Stock>> copy) {
        super(copy);
    }

    // IO
    public MarketData<StockFuturesData, StockFuturesOrderData> toData() {
        MarketData<StockFuturesData, StockFuturesOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = (StockFuturesData) ((StackableNote<Stock>) getUnitAsset()).toData();

        for (Order<StackableNote<Stock>> o : getOrders()) {
            StockFuturesOrder so = (StockFuturesOrder) o;
            data.orders.add(so.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static StockFuturesMarket fromData(MarketData<StockFuturesData, StockFuturesOrderData> data, LedgerState state) {
        StockFuturesMarket m = new StockFuturesMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                StackableNote.fromData(data.unitAsset, state),
                data.tickSize
        );

        m.setPrice(data.price);

        for (StockFuturesOrderData od : data.orders) {
            m.placeOrder(StockFuturesOrder.fromData(od, state));
        }

        return m;
    }
}
