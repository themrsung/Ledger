package jbs.ledger.classes;

import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.io.types.accounts.AssetholderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.portfolios.basic.CashPortfolio;
import jbs.ledger.types.portfolios.basic.CommodityPortfolio;
import jbs.ledger.types.portfolios.basic.StockPortfolio;
import jbs.ledger.types.portfolios.synthetic.StackableNotePortfolio;
import jbs.ledger.types.portfolios.synthetic.UniqueNotePortfolio;

import java.util.UUID;

/**
 * An entity capable of holding assets.
 */
public final class Assetholder implements Economic {
    /**
     * Creates a blank instance
     * @param uniqueId Unique ID of this assetholder
     * @param name Name of this assetholder
     */
    public Assetholder(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;

        this.cash = new CashPortfolio();
        this.commodities = new CommodityPortfolio();
        this.stocks = new StockPortfolio();
        this.bonds = new StackableNotePortfolio<>();

        this.notes = new UniqueNotePortfolio<>();
        this.commodityForwards = new UniqueNotePortfolio<>();
        this.stockForwards = new UniqueNotePortfolio<>();
        this.commodityFutures = new StackableNotePortfolio<>();
        this.stockFutures = new StackableNotePortfolio<>();
    }

    public Assetholder(Assetholder copy) {
        this.uniqueId = copy.uniqueId;
        this.name = copy.name;

        this.cash = copy.cash;
        this.commodities = copy.commodities;
        this.stocks = copy.stocks;
        this.bonds = copy.bonds;

        this.notes = copy.notes;
        this.commodityForwards = copy.commodityForwards;
        this.stockForwards = copy.stockForwards;

        this.commodityFutures = copy.commodityFutures;
        this.stockFutures = copy.stockFutures;
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
        return uniqueId;
    }

    /**
     * Name of this entity. Not necessarily unique.
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Basic assets
    private final CashPortfolio cash;
    private final CommodityPortfolio commodities;
    private final StockPortfolio stocks;
    private final StackableNotePortfolio<Cash> bonds;

    // Unique Notes
    private final UniqueNotePortfolio<Cash> notes;
    private final UniqueNotePortfolio<Commodity> commodityForwards;
    private final UniqueNotePortfolio<Stock> stockForwards;

    // Stackable Notes (Futures)
    private final StackableNotePortfolio<Commodity> commodityFutures;
    private final StackableNotePortfolio<Stock> stockFutures;

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
    public StackableNotePortfolio<Cash> getBonds() {
        return bonds;
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
    public StackableNotePortfolio<Commodity> getCommodityFutures() {
        return commodityFutures;
    }

    @Override
    public StackableNotePortfolio<Stock> getStockFutures() {
        return stockFutures;
    }

    // IO
    public static Assetholder getEmptyInstance(UUID uniqueId) {
        return new Assetholder(uniqueId);
    }

    private Assetholder(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.name = null;

        this.cash = new CashPortfolio();
        this.commodities = new CommodityPortfolio();
        this.stocks = new StockPortfolio();
        this.bonds = new StackableNotePortfolio<>();

        this.notes = new UniqueNotePortfolio<>();
        this.commodityForwards = new UniqueNotePortfolio<>();
        this.stockForwards = new UniqueNotePortfolio<>();
        this.commodityFutures = new StackableNotePortfolio<>();
        this.stockFutures = new StackableNotePortfolio<>();
    }

    public void load(AssetholderData data, LedgerState state) {
        this.name = data.name;

        this.cash.add(CashPortfolio.fromData(data.cash));
        this.commodities.add(CommodityPortfolio.fromData(data.commodities));
        this.stocks.add(StockPortfolio.fromData(data.stocks));
        this.bonds.add(StackableNotePortfolio.fromBondData(data.bonds, state));

        this.notes.add(UniqueNotePortfolio.fromNoteData(data.notes, state));
        this.commodityForwards.add(UniqueNotePortfolio.fromCommodityForwardData(data.commodityForwards, state));
        this.stockForwards.add(UniqueNotePortfolio.fromStockForwardData(data.stockForwards, state));

        this.commodityFutures.add(StackableNotePortfolio.fromCommodityFuturesData(data.commodityFutures, state));
        this.stockFutures.add(StackableNotePortfolio.fromStockFuturesData(data.stockFutures, state));
    }

    public AssetholderData toData() {
        AssetholderData data = new AssetholderData();

        data.uniqueId = uniqueId;
        data.name = name;

        data.cash = cash.toData();
        data.commodities = commodities.toData();
        data.stocks = stocks.toData();
        data.bonds = bonds.toBondData();

        data.notes = notes.toNoteData();
        data.commodityForwards = notes.toCommodityForwardData();
        data.stockForwards = stockForwards.toStockForwardData();

        data.commodityFutures = commodityFutures.toCommodityFuturesData();
        data.stockFutures = stockFutures.toStockFuturesData();

        return data;
    }
}
