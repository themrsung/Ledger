package jbs.ledger.classes.markets.basic;

import jbs.ledger.classes.markets.AbstractMarket;
import jbs.ledger.classes.orders.basic.BondOrder;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.orders.Order;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.unique.BondForwardData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.BondOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import java.util.ArrayList;
import java.util.UUID;

public final class BondMarket extends AbstractMarket<StackableNote<Cash>> {
    public BondMarket(
            UUID uniqueId,
            String symbol,
            Economic exchange,
            String currency,
            StackableNote<Cash> unitAsset,
            double tickSize
    ) {
        super(uniqueId, symbol, exchange, currency, unitAsset, tickSize);
    }

    public BondMarket(AbstractMarket<StackableNote<Cash>> copy) {
        super(copy);
    }

    @Override
    public StackableNote<Cash> getUnitAsset() {
        return unitAsset.copy();
    }

    // IO
    public MarketData<BondData, BondOrderData> toData() {
        MarketData<BondData, BondOrderData> data = new MarketData<>();

        data.uniqueId = getUniqueId();
        data.symbol = getSymbol();
        data.exchange = getExchange().getUniqueId();
        data.currency = getCurrency();
        data.unitAsset = (BondData) ((StackableNote<Cash>) getUnitAsset()).toData();

        for (Order<StackableNote<Cash>> o : getOrders()) {
            BondOrder bo = (BondOrder) o;
            data.orders.add(bo.toData());
        }

        data.price = getPrice();
        data.tickSize = getTickSize();

        return data;
    }

    public static BondMarket fromData(MarketData<BondData, BondOrderData> data, LedgerState state) {
        BondMarket m = new BondMarket(
                data.uniqueId,
                data.symbol,
                state.getAssetholder(data.exchange),
                data.currency,
                StackableNote.fromData(data.unitAsset, state),
                data.tickSize
        );

        m.setPrice(data.price);

        for (BondOrderData od : data.orders) {
            m.placeOrder(BondOrder.fromData(od, state));
        }

        return m;
    }
}
