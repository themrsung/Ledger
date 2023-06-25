package jbs.ledger.classes.markets.basic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.basic.BondOrder;
import jbs.ledger.classes.orders.basic.ForexOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.BondOrderData;
import jbs.ledger.io.types.orders.basic.ForexOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.UUID;

public final class ForexMarket extends AbstractMarket<Cash> {
    public ForexMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            Cash unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public ForexMarket(AbstractMarket<Cash> copy) {
        super(copy);
    }

    @Override
    public Cash getUnitAsset() {
        return unitAsset.copy();
    }

    // IO
    public MarketData<CashData, ForexOrderData> toData() {
        MarketData<CashData, ForexOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = ((Cash) getUnitAsset()).toData();

        for (Order<Cash> o : getOrders()) {
            ForexOrder fo = (ForexOrder) o;
            data.orders.add(fo.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static ForexMarket fromData(MarketData<CashData, ForexOrderData> data, LedgerState state) {
        ForexMarket m = new ForexMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                Cash.fromData(data.unitAsset),
                data.tickSize
        );

        m.setPrice(data.price);

        for (ForexOrderData od : data.orders) {
            m.placeOrder(ForexOrder.fromData(od, state));
        }

        return m;
    }
}
