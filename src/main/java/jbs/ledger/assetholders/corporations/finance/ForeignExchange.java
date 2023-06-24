package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.markets.basic.ForexMarket;
import jbs.ledger.io.types.assetholders.corporations.finance.ForexData;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.markets.MarketData;
import jbs.ledger.io.types.orders.basic.ForexOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

/**
 * FOREXs can process foreign currency orders.
 */
public final class ForeignExchange extends Corporation {
    public ForeignExchange(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);

        this.forexMarkets = new ArrayList<>();
    }

    public ForeignExchange(Corporation copy) {
        super(copy);

        this.forexMarkets = new ArrayList<>();
    }

    private final ArrayList<ForexMarket> forexMarkets;

    public ArrayList<ForexMarket> getForexMarkets() {
        return new ArrayList<>(forexMarkets);
    }

    public void addForexMarket(ForexMarket market) {
        forexMarkets.add(market);
    }

    public boolean removeForexMarket(ForexMarket market) {
        return forexMarkets.remove(market);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.FOREIGN_EXCHANGE;
    }




    // IO
    @Override
    public ForexData toData() {
        ForexData data = new ForexData(super.toData());

        for (ForexMarket market : forexMarkets) {
            data.markets.add(market.toData());
        }

        return data;
    }

    public static ForeignExchange getEmptyInstance(UUID uniqueId) {
        return new ForeignExchange(uniqueId);
    }

    private ForeignExchange(UUID uniqueId) {
        super(uniqueId);

        this.forexMarkets = new ArrayList<>();
    }

    public void load(ForexData data, LedgerState state) {
        super.load(data, state);

        for (MarketData<CashData, ForexOrderData> market : data.markets) {
            forexMarkets.add(ForexMarket.fromData(market, state));
        }
    }
}
