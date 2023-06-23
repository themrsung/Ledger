package jbs.ledger.io.types.accounts;

import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.io.types.assets.basic.StockData;
import jbs.ledger.io.types.assets.synthetic.ConditionalNoteData;
import jbs.ledger.io.types.assets.synthetic.StackableNoteData;
import jbs.ledger.io.types.assets.synthetic.UniqueNoteData;
import jbs.ledger.io.types.portfolios.PortfolioData;

import java.util.UUID;

public class AssetholderData {
    public AssetholderData() {}

    public UUID uniqueId;

    // BASIC
    public PortfolioData<CashData> cash;
    public PortfolioData<CommodityData> commodities;
    public PortfolioData<StockData> stocks;

    // UNIQUE NOTES
    public PortfolioData<UniqueNoteData<CashData>> notes;
    public PortfolioData<UniqueNoteData<CommodityData>> commodityForwards;
    public PortfolioData<UniqueNoteData<StockData>> stockForwards;

    // STACKABLE NOTES
    public PortfolioData<StackableNoteData<CashData>> bonds;
    public PortfolioData<StackableNoteData<CommodityData>> commodityFutures;
    public PortfolioData<StackableNoteData<StockData>> stockFutures;

    // CONDITIONAL NOTES
    public PortfolioData<ConditionalNoteData<CashData>> forexOptions;
    public PortfolioData<ConditionalNoteData<CommodityData>> commodityOptions;
    public PortfolioData<ConditionalNoteData<StockData>> stockOptions;
}
