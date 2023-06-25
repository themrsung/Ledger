package jbs.ledger.classes.markets.synthetic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.synthetic.CashOptionOrder;
import jbs.ledger.classes.orders.synthetic.StockOptionOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.synthetic.stackable.CashOptionData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockOptionData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.synthetic.CashOptionOrderData;
import jbs.ledger.io.types.orders.synthetic.StockOptionOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;

import java.util.UUID;

public final class StockOptionMarket extends AbstractMarket<Option<Stock>> {
    public StockOptionMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            Option<Stock> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public StockOptionMarket(AbstractMarket<Option<Stock>> copy) {
        super(copy);
    }

    @Override
    public Option<Stock> getUnitAsset() {
        return unitAsset.copy();
    }

    // IO
    public MarketData<StockOptionData, StockOptionOrderData> toData() {
        MarketData<StockOptionData, StockOptionOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = (StockOptionData) getUnitAsset().toData();

        for (Order<Option<Stock>> o : getOrders()) {
            StockOptionOrder so = (StockOptionOrder) o;
            data.orders.add(so.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static StockOptionMarket fromData(MarketData<StockOptionData, StockOptionOrderData> data, LedgerState state) {
        StockOptionMarket m = new StockOptionMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                Option.fromData(data.unitAsset, state),
                data.tickSize
        );

        m.setPrice(data.price);

        for (StockOptionOrderData od : data.orders) {
            m.placeOrder(StockOptionOrder.fromData(od, state));
        }

        return m;
    }
}
