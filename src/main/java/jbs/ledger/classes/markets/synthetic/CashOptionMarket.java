package jbs.ledger.classes.markets.synthetic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.synthetic.CashOptionOrder;
import jbs.ledger.classes.orders.synthetic.StockFuturesOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.synthetic.stackable.CashOptionData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.synthetic.CashOptionOrderData;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class CashOptionMarket extends AbstractMarket<Option<Cash>> {
    public CashOptionMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            Option<Cash> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public CashOptionMarket(AbstractMarket<Option<Cash>> copy) {
        super(copy);
    }

    @Override
    public Option<Cash> getUnitAsset() {
        return unitAsset.copy();
    }

    // IO
    public MarketData<CashOptionData, CashOptionOrderData> toData() {
        MarketData<CashOptionData, CashOptionOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = (CashOptionData) getUnitAsset().toData();

        for (Order<Option<Cash>> o : getOrders()) {
            CashOptionOrder so = (CashOptionOrder) o;
            data.orders.add(so.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static CashOptionMarket fromData(MarketData<CashOptionData, CashOptionOrderData> data, LedgerState state) {
        CashOptionMarket m = new CashOptionMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                Option.fromData(data.unitAsset, state),
                data.tickSize
        );

        m.setPrice(data.price);

        for (CashOptionOrderData od : data.orders) {
            m.placeOrder(CashOptionOrder.fromData(od, state));
        }

        return m;
    }
}
