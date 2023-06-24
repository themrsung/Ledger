package jbs.ledger.io.types.assetholders;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.assets.synthetic.unique.BondForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.navigation.Address;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class AssetholderData {
    public AssetholderData(AssetholderData copy) {
        this.type = copy.type;
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
        this.address = copy.address;
    }

    public AssetholderData() {}

    public AssetholderType type;

    public UUID uniqueId;
    public String name;

    // BASIC
    public ArrayList<CashData> cash = new ArrayList<>();
    public ArrayList<CommodityData> commodities = new ArrayList<>();
    public ArrayList<StockData> stocks = new ArrayList<>();
    public ArrayList<BondData> bonds = new ArrayList<>();

    // UNIQUE NOTES
    public ArrayList<NoteData> notes = new ArrayList<>();
    public ArrayList<CommodityForwardData> commodityForwards = new ArrayList<>();
    public ArrayList<StockForwardData> stockForwards = new ArrayList<>();
    public ArrayList<BondForwardData> bondForwards = new ArrayList<>();

    // FUTURES
    public ArrayList<CommodityFuturesData> commodityFutures = new ArrayList<>();
    public ArrayList<StockFuturesData> stockFutures = new ArrayList<>();

    // Address
    @Nullable
    public Address address = null;
}
