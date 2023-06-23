package jbs.ledger.types.portfolios.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.io.types.assets.synthetic.stackable.StackableNoteData;
import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.UniqueNoteData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.assets.synthetic.UniqueNote;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

public final class UniqueNotePortfolio<D extends Asset> extends AbstractPortfolio<UniqueNote<D>> {
    public UniqueNotePortfolio(UniqueNotePortfolio<D> copy) {
        super(copy);
    }
    public UniqueNotePortfolio() {
        super();
    }

    @Nullable
    @Override
    public UniqueNote<D> get(String symbol) {
        for (UniqueNote<D> note : get()) {
            if (note.getSymbol().equalsIgnoreCase(symbol)) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void add(UniqueNote<D> asset) {
        super.add(asset);

        clean();
    }

    @Override
    public void remove(UniqueNote<D> asset) {
        getRaw().remove(asset);
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getQuantity() == 0L);
    }

    // IO
    public static UniqueNotePortfolio<Cash> fromNoteData(ArrayList<NoteData> data, LedgerState state) {
        UniqueNotePortfolio<Cash> portfolio = new UniqueNotePortfolio<>();

        for (NoteData note : data) {
            portfolio.add(UniqueNote.fromData(note, state));
        }

        return portfolio;
    }

    public static UniqueNotePortfolio<Commodity> fromCommodityForwardData(ArrayList<CommodityForwardData> data, LedgerState state) {
        UniqueNotePortfolio<Commodity> portfolio = new UniqueNotePortfolio<>();

        for (CommodityForwardData note : data) {
            portfolio.add(UniqueNote.fromData(note, state));
        }

        return portfolio;
    }

    public static UniqueNotePortfolio<Stock> fromStockForwardData(ArrayList<StockForwardData> data, LedgerState state) {
        UniqueNotePortfolio<Stock> portfolio = new UniqueNotePortfolio<>();

        for (StockForwardData note : data) {
            portfolio.add(UniqueNote.fromData(note, state));
        }

        return portfolio;
    }

    public ArrayList<NoteData> toNoteData() {
        ArrayList<NoteData> data = new ArrayList<>();

        for (UniqueNote<D> a : get()) {
            data.add((NoteData) a.toData());
        }

        return data;
    }

    public ArrayList<CommodityForwardData> toCommodityForwardData() {
        ArrayList<CommodityForwardData> data = new ArrayList<>();

        for (UniqueNote<D> a : get()) {
            data.add((CommodityForwardData) a.toData());
        }

        return data;
    }

    public ArrayList<StockForwardData> toStockForwardData() {
        ArrayList<StockForwardData> data = new ArrayList<>();

        for (UniqueNote<D> a : get()) {
            data.add((StockForwardData) a.toData());
        }

        return data;
    }
}
