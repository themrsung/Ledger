package jbs.ledger.types.portfolios.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.assets.synthetic.stackable.CommodityFuturesData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockFuturesData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

public final class StackableNotePortfolio<D extends Asset> extends AbstractPortfolio<StackableNote<D>> {
    public StackableNotePortfolio(StackableNotePortfolio<D> copy) {
        super(copy);
    }
    public StackableNotePortfolio() {
        super();
    }

    @Nullable
    @Override
    public StackableNote<D> get(String symbol) {
        for (StackableNote<D> note : get()) {
            if (note.getSymbol().equalsIgnoreCase(symbol)) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void add(StackableNote<D> asset) {
        StackableNote<D> existing = getRaw(asset.getSymbol());

        if (existing != null && existing.isStackable(asset)) {
            existing.addQuantity(asset.getQuantity());
            return;
        }

        super.add(asset);

        clean();
    }

    @Override
    public void remove(StackableNote<D> asset) {
        add(asset.negate());
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getQuantity() == 0);
    }

    // IO
    public static StackableNotePortfolio<Cash> fromBondData(ArrayList<BondData> data, LedgerState state) {
        StackableNotePortfolio<Cash> portfolio = new StackableNotePortfolio<>();

        for (BondData note : data) {
            portfolio.getRaw().add(StackableNote.fromData(note, state));
        }

        return portfolio;
    }

    public static StackableNotePortfolio<Commodity> fromCommodityFuturesData(ArrayList<CommodityFuturesData> data, LedgerState state) {
        StackableNotePortfolio<Commodity> portfolio = new StackableNotePortfolio<>();

        for (CommodityFuturesData note : data) {
            portfolio.getRaw().add(StackableNote.fromData(note, state));
        }

        return portfolio;
    }

    public static StackableNotePortfolio<Stock> fromStockFuturesData(ArrayList<StockFuturesData> data, LedgerState state) {
        StackableNotePortfolio<Stock> portfolio = new StackableNotePortfolio<>();

        for (StockFuturesData note : data) {
            portfolio.getRaw().add(StackableNote.fromData(note, state));
        }

        return portfolio;
    }

    public ArrayList<BondData> toBondData() {
        ArrayList<BondData> data = new ArrayList<>();

        for (StackableNote<D> a : get()) {
            data.add((BondData) a.toData());
        }

        return data;
    }

    public ArrayList<CommodityFuturesData> toCommodityFuturesData() {
        ArrayList<CommodityFuturesData> data = new ArrayList<>();

        for (StackableNote<D> a : get()) {
            data.add((CommodityFuturesData) a.toData());
        }

        return data;
    }

    public ArrayList<StockFuturesData> toStockFuturesData() {
        ArrayList<StockFuturesData> data = new ArrayList<>();

        for (StackableNote<D> a : get()) {
            data.add((StockFuturesData) a.toData());
        }

        return data;
    }
}
