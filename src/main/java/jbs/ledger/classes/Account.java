package jbs.ledger.classes;

import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.Cash;
import jbs.ledger.types.assets.Commodity;
import jbs.ledger.types.assets.Stock;
import jbs.ledger.types.portfolios.*;

import java.util.UUID;

public class Account implements Economic {
    public Account() {
        this.uniqueId = null;
        this.ownerId = null;

        this.cash = new CashPortfolio();
        this.commodities = new CommodityPortfolio();
        this.stocks = new StockPortfolio();

        this.notes = new UniqueNotePortfolio<>();
        this.commodityForwards = new UniqueNotePortfolio<>();
        this.stockForwards = new UniqueNotePortfolio<>();

        this.bonds = new StackableNotePortfolio<>();
        this.commodityFutures = new StackableNotePortfolio<>();
        this.stockFutures = new StackableNotePortfolio<>();

        this.forexOptions = new ConditionalNotePortfolio<>();
        this.commodityOptions = new ConditionalNotePortfolio<>();
        this.stockOptions = new ConditionalNotePortfolio<>();
    }


    // Identification

    /**
     * Unique ID of this account.
     */
    private final UUID uniqueId;

    /**
     * Unique ID of owner.
     */
    private UUID ownerId;

    /**
     * Gets Unique ID of this account.
     * @return UUID of this account
     */
    @Override
    public UUID getUniqueId() {
        return null;
    }

    /**
     * Gets Unique ID of owner.
     * @return UUID of owner
     */
    public UUID getOwnerId() {
        return ownerId;
    }

    // Assets
    private final CashPortfolio cash;
    private final CommodityPortfolio commodities;
    private final StockPortfolio stocks;

    private final UniqueNotePortfolio<Cash> notes;
    private final UniqueNotePortfolio<Commodity> commodityForwards;
    private final UniqueNotePortfolio<Stock> stockForwards;

    private final StackableNotePortfolio<Cash> bonds;
    private final StackableNotePortfolio<Commodity> commodityFutures;
    private final StackableNotePortfolio<Stock> stockFutures;

    private final ConditionalNotePortfolio<Cash> forexOptions;
    private final ConditionalNotePortfolio<Commodity> commodityOptions;
    private final ConditionalNotePortfolio<Stock> stockOptions;

    @Override
    public CashPortfolio getCash() {
        return cash;
    }

    @Override
    public CommodityPortfolio getCommodities() {
        return commodities;
    }

    @Override
    public StockPortfolio getStocks() {
        return stocks;
    }

    @Override
    public UniqueNotePortfolio<Cash> getNotes() {
        return notes;
    }

    @Override
    public UniqueNotePortfolio<Commodity> getCommodityForwards() {
        return commodityForwards;
    }

    @Override
    public UniqueNotePortfolio<Stock> getStockForwards() {
        return stockForwards;
    }

    @Override
    public StackableNotePortfolio<Cash> getBonds() {
        return bonds;
    }

    @Override
    public StackableNotePortfolio<Commodity> getCommodityFutures() {
        return commodityFutures;
    }

    @Override
    public StackableNotePortfolio<Stock> getStockFutures() {
        return stockFutures;
    }

    @Override
    public ConditionalNotePortfolio<Cash> getForexOptions() {
        return forexOptions;
    }

    @Override
    public ConditionalNotePortfolio<Commodity> getCommodityOptions() {
        return commodityOptions;
    }

    @Override
    public ConditionalNotePortfolio<Stock> getStockOptions() {
        return stockOptions;
    }
}
