package jbs.ledger.types.portfolios.basic;

import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.portfolios.AbstractPortfolio;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.ArrayList;

public final class CashPortfolio extends AbstractPortfolio<Cash> {
    public CashPortfolio(CashPortfolio copy) {
        super();
    }
    public CashPortfolio() {
        super();
    }

    @Nullable
    @Override
    public Cash get(String symbol) {
        for (Cash c : get()) {
            if (c.getSymbol().equalsIgnoreCase(symbol)) {
                return c.copy();
            }
        }

        return null;
    }

    @Override
    public void add(Cash asset) {
        Cash existing = getRaw(asset.getSymbol());

        if (existing != null && existing.isStackable(asset)) {
            existing.addBalance(asset.getBalance());
            return;
        }

        super.add(asset.copy());

        clean();
    }

    @Override
    public void remove(Cash asset) {
        add(asset.negate());
    }

    @Override
    public boolean contains(Cash asset) {
        Cash exiting = get(asset.getSymbol());

        if (exiting != null) {
            return exiting.getBalance() >= asset.getBalance();
        }

        return super.contains(asset);
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getBalance() == 0d);
    }

    // IO
    public static CashPortfolio fromData(ArrayList<CashData> data) {
        CashPortfolio portfolio = new CashPortfolio();

        for (CashData cd : data) {
            portfolio.add(Cash.fromData(cd));
        }

        return portfolio;
    }

    public ArrayList<CashData> toData() {
        ArrayList<CashData> data = new ArrayList<>();

        for (Cash c : get()) {
            data.add(c.toData());
        }

        return data;
    }
}
