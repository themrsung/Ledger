package jbs.ledger.assetholders;

import jbs.ledger.interfaces.address.Headquartered;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Searchable;
import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.navigation.Address;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.credit.CreditRating;
import jbs.ledger.types.portfolios.basic.CashPortfolio;
import jbs.ledger.types.portfolios.basic.CommodityPortfolio;
import jbs.ledger.types.portfolios.basic.StockPortfolio;
import jbs.ledger.types.portfolios.synthetic.OptionPortfolio;
import jbs.ledger.types.portfolios.synthetic.StackableNotePortfolio;
import jbs.ledger.types.portfolios.synthetic.UniqueNotePortfolio;
import jbs.ledger.utils.TypeUtils;
import org.bukkit.Location;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * An entity capable of holding assets.
 */
public abstract class Assetholder implements Economic, Headquartered, Searchable {
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
        this.bondForwards = new UniqueNotePortfolio<>();

        this.commodityFutures = new StackableNotePortfolio<>();
        this.stockFutures = new StackableNotePortfolio<>();

        this.cashOptions = new OptionPortfolio<>();
        this.stockOptions = new OptionPortfolio<>();

        this.address = null;
        this.previousLocation = null;

        this.creditScore = CreditRating.B.toScore();
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
        this.bondForwards = copy.bondForwards;

        this.commodityFutures = copy.commodityFutures;
        this.stockFutures = copy.stockFutures;

        this.cashOptions = copy.cashOptions;
        this.stockOptions = copy.stockOptions;

        this.address = copy.address;
        this.previousLocation = copy.previousLocation;

        this.creditScore = copy.creditScore;
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

    @Override
    public String getSearchTag() {
        return getName();
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
    private final UniqueNotePortfolio<StackableNote<Cash>> bondForwards;

    // Stackable Notes (Futures)
    private final StackableNotePortfolio<Commodity> commodityFutures;
    private final StackableNotePortfolio<Stock> stockFutures;

    // Options
    private final OptionPortfolio<Cash> cashOptions;
    private final OptionPortfolio<Stock> stockOptions;

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
    public UniqueNotePortfolio<StackableNote<Cash>> getBondForwards() {
        return bondForwards;
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
    public OptionPortfolio<Cash> getCashOptions() {
        return cashOptions;
    }

    @Override
    public OptionPortfolio<Stock> getStockOptions() {
        return stockOptions;
    }



    // Address
    @Nullable
    private Location address;
    @Nullable
    private transient Location previousLocation;

    @Override
    @Nullable
    public Location getAddress() {
        return address;
    }

    @Override
    public void setAddress(@Nullable Location address) {
        this.address = address;
    }

    @Override
    @Nullable
    public Location getPreviousLocation() {
        return previousLocation;
    }

    @Override
    public void setPreviousLocation(@Nullable Location address) {
        this.previousLocation = address;
    }

    @Override
    public long getProtectionRadius() {
        return 0;
    }

    // Credit
    private float creditScore;

    @Override
    public float getCreditScore() {
        return creditScore;
    }
    @Override
    public void setCreditScore(float creditScore) {
        this.creditScore = creditScore;
    }

    // Type
    public abstract AssetholderType getType();

    // IO
    protected Assetholder(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.name = null;

        this.cash = new CashPortfolio();
        this.commodities = new CommodityPortfolio();
        this.stocks = new StockPortfolio();
        this.bonds = new StackableNotePortfolio<>();

        this.notes = new UniqueNotePortfolio<>();
        this.commodityForwards = new UniqueNotePortfolio<>();
        this.stockForwards = new UniqueNotePortfolio<>();
        this.bondForwards = new UniqueNotePortfolio<>();

        this.commodityFutures = new StackableNotePortfolio<>();
        this.stockFutures = new StackableNotePortfolio<>();

        this.cashOptions = new OptionPortfolio<>();
        this.stockOptions = new OptionPortfolio<>();

        this.address = null;
        this.previousLocation = null;

        this.creditScore = CreditRating.B.toScore();
    }

    /**
     * Loads parameters from data class.
     * This needs to be called AT LEAST TWICE. (Market pointers in options can be null if called once)
     * @param data Data to initialize from
     * @param state Current state
     */
    public void load(AssetholderData data, LedgerState state) {
        this.name = data.name;

        this.cash.nuke();
        this.commodities.nuke();
        this.stocks.nuke();
        this.bonds.nuke();

        this.cash.add(CashPortfolio.fromData(data.cash));
        this.commodities.add(CommodityPortfolio.fromData(data.commodities));
        this.stocks.add(StockPortfolio.fromData(data.stocks));
        this.bonds.add(StackableNotePortfolio.fromBondData(data.bonds, state));

        this.notes.nuke();
        this.commodityForwards.nuke();
        this.stockForwards.nuke();

        this.notes.add(UniqueNotePortfolio.fromNoteData(data.notes, state));
        this.commodityForwards.add(UniqueNotePortfolio.fromCommodityForwardData(data.commodityForwards, state));
        this.stockForwards.add(UniqueNotePortfolio.fromStockForwardData(data.stockForwards, state));

        this.commodityFutures.nuke();
        this.stockFutures.nuke();

        this.commodityFutures.add(StackableNotePortfolio.fromCommodityFuturesData(data.commodityFutures, state));
        this.stockFutures.add(StackableNotePortfolio.fromStockFuturesData(data.stockFutures, state));

        this.cashOptions.nuke();
        this.stockOptions.nuke();

        this.cashOptions.add(OptionPortfolio.fromCashOptionData(data.cashOptions, state));
        this.stockOptions.add(OptionPortfolio.fromStockOptionData(data.stockOptions, state));

        this.creditScore = data.creditScore;


        if (data.address != null) this.address = TypeUtils.addressToLocation(data.address);
    }

    public AssetholderData toData() {
        AssetholderData data = new AssetholderData();

        data.type = this.getType();

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

        data.creditScore = creditScore;

        if (address != null) data.address = new Address(address);

        return data;
    }
}
