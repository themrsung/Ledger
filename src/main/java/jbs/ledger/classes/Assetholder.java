package jbs.ledger.classes;

import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.portfolios.basic.CashPortfolio;
import jbs.ledger.types.portfolios.basic.CommodityPortfolio;
import jbs.ledger.types.portfolios.basic.StockPortfolio;
import jbs.ledger.types.portfolios.synthetic.ConditionalNotePortfolio;
import jbs.ledger.types.portfolios.synthetic.StackableNotePortfolio;
import jbs.ledger.types.portfolios.synthetic.UniqueNotePortfolio;

import java.util.UUID;

/**
 * An entity capable of holding assets.
 * Use the UUID to translate identities between Ledger and your plugin.
 */
public final class Assetholder implements Economic {
    public Assetholder(UUID uniqueId) {
        this.uniqueId = uniqueId;

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

    public Assetholder(Assetholder copy) {
        this.uniqueId = copy.uniqueId;

        this.cash = copy.cash;
        this.commodities = copy.commodities;
        this.stocks = copy.stocks;

        this.notes = copy.notes;
        this.commodityForwards = copy.commodityForwards;
        this.stockForwards = copy.stockForwards;

        this.bonds = copy.bonds;
        this.commodityFutures = copy.commodityFutures;
        this.stockFutures = copy.stockFutures;

        this.forexOptions = copy .forexOptions;
        this.commodityOptions = copy.commodityOptions;
        this.stockOptions = copy.stockOptions;
    }

    public Assetholder() {
        this.uniqueId = null;

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
     * Unique ID of this entity.
     */
    private final UUID uniqueId;

    /**
     * Gets Unique ID of this account.
     * @return UUID of this account
     */
    @Override
    public UUID getUniqueId() {
        return null;
    }

    // Basic assets
    private final CashPortfolio cash;
    private final CommodityPortfolio commodities;
    private final StockPortfolio stocks;

    // Unique Notes
    private final UniqueNotePortfolio<Cash> notes;
    private final UniqueNotePortfolio<Commodity> commodityForwards;
    private final UniqueNotePortfolio<Stock> stockForwards;

    // Stackable Notes
    private final StackableNotePortfolio<Cash> bonds;
    private final StackableNotePortfolio<Commodity> commodityFutures;
    private final StackableNotePortfolio<Stock> stockFutures;

    // Conditional Notes
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
