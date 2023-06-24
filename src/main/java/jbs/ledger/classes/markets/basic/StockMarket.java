package jbs.ledger.classes.markets.basic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.basic.ForexOrder;
import jbs.ledger.classes.orders.basic.StockOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.ForexOrderData;
import jbs.ledger.io.types.orders.basic.StockOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;

import java.util.UUID;

public final class StockMarket extends AbstractMarket<Stock> {
    public StockMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            Stock unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public StockMarket(AbstractMarket<Stock> copy) {
        super(copy);
    }


    // IO
    public MarketData<StockData, StockOrderData> toData() {
        MarketData<StockData, StockOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = ((Stock) getUnitAsset()).toData();

        for (Order<Stock> o : getOrders()) {
            StockOrder so = (StockOrder) o;
            data.orders.add(so.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static StockMarket fromData(MarketData<StockData, StockOrderData> data, LedgerState state) {
        StockMarket m = new StockMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                Stock.fromData(data.unitAsset),
                data.tickSize
        );

        m.setPrice(data.price);

        for (StockOrderData od : data.orders) {
            m.placeOrder(StockOrder.fromData(od, state));
        }

        return m;
    }
}
