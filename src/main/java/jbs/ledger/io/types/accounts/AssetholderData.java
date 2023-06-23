package jbs.ledger.io.types.accounts;

import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;

import java.util.ArrayList;
import java.util.UUID;

public final class AssetholderData {
    public AssetholderData() {}

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

    // FUTURES
    public ArrayList<CommodityFuturesData> commodityFutures = new ArrayList<>();
    public ArrayList<StockFuturesData> stockFutures = new ArrayList<>();

}
