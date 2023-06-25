package jbs.ledger.timers.economy;

import jbs.ledger.Ledger;
import jbs.ledger.interfaces.markets.Market;
import org.bukkit.Bukkit;

public final class MarketTicker implements Runnable {
    public MarketTicker(Ledger ledger) {
        this.ledger = ledger;
    }
    private final Ledger ledger;


    @Override
    public void run() {
        for (Market<?> m : ledger.getState().getMarkets()) {
            m.processOrders();
        }
    }
}
