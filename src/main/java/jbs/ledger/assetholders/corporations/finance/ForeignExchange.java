package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.markets.basic.ForexMarket;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

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
}
