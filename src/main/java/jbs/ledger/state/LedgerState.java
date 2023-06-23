package jbs.ledger.state;

import jbs.ledger.classes.Assetholder;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.markets.Market;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The running state of this plugin.
 */
public final class LedgerState {
    public LedgerState() {
        this.assetholders = new ArrayList<>();
        this.markets = new ArrayList<>();
    }

    private final ArrayList<Assetholder> assetholders;
    private final ArrayList<Market<? extends Asset>> markets;

    // Assetholders

    /**
     * Gets all assetholders in existence.
     * @return Returns a copied list of assetholders.
     */
    public ArrayList<Assetholder> getAssetholders() {
        return new ArrayList<>(assetholders);
    }

    @Nullable
    public Assetholder getAssetholder(UUID accountId) {
        for (Assetholder a : getAssetholders()) {
            if (a.getUniqueId().equals(accountId)) {
                return a;
            }
        }

        return null;
    }

    public void addAssetholder(Assetholder assetholder) {
        this.assetholders.add(assetholder);
    }
    public boolean removeAssetholder(Assetholder assetholder) {
        return this.assetholders.remove(assetholder);
    }

    // Markets

    /**
     * Gets all markets in this plugin is aware of.
     * Keep in mind that you have to add your market to this list for automated option assignment to work.
     * @return Returns a copied list of markets.
     */
    public ArrayList<Market<? extends Asset>> getMarkets() {
        return new ArrayList<>(markets);
    }

    @Nullable
    public Market<?> getMarket(UUID marketId) {
        for (Market<?> m : getMarkets()) {
            if (m.getUniqueId().equals(marketId)) {
                return m;
            }
        }
        return null;
    }

    public void addMarket(Market<? extends Asset> market) {
        this.markets.add(market);
    }

    public boolean removeMarket(Market<? extends Asset> market) {
        return this.markets.remove(market);
    }

    // Interface getters

    /**
     * Gets everything that is unique. (Accounts, Markets, Orders, etc.)
     * @return Returns a copied list of uniques.
     */
    public ArrayList<Unique> getUniques() {
        ArrayList<Unique> uniques = new ArrayList<>();

        ArrayList<Assetholder> assetholders = getAssetholders();
        ArrayList<Market<? extends Asset>> markets = getMarkets();

        uniques.addAll(assetholders);
        uniques.addAll(markets);

        for (Assetholder a : assetholders) {
            uniques.addAll(a.getNotes().get());
            uniques.addAll(a.getCommodityForwards().get());
            uniques.addAll(a.getStockForwards().get());
        }

        for (Market<?> m : markets) {
            uniques.addAll(m.getOrders());
        }


        return uniques;
    }
}
