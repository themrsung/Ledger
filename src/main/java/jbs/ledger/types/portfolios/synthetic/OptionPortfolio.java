package jbs.ledger.types.portfolios.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.io.types.assets.synthetic.stackable.CashOptionData;
import jbs.ledger.io.types.assets.synthetic.stackable.StockOptionData;
import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;
import jbs.ledger.types.assets.synthetic.UniqueNote;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;

public final class OptionPortfolio<D extends Asset> extends AbstractPortfolio<Option<D>> {
    public OptionPortfolio(OptionPortfolio<D> copy) {
        super(copy);
    }
    public OptionPortfolio() {
        super();
    }

    @Nullable
    @Override
    public Option<D> get(String symbol) {
        for (Option<D> note : get()) {
            if (note.getSymbol().equalsIgnoreCase(symbol)) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void add(Option<D> asset) {
        super.add(asset);

        clean();
    }

    @Override
    public void remove(Option<D> asset) {
        getRaw().remove(asset);
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getQuantity() == 0L);
    }

    // IO
    public static OptionPortfolio<Cash> fromCashOptionData(ArrayList<CashOptionData> data, LedgerState state) {
        OptionPortfolio<Cash> portfolio = new OptionPortfolio<>();

        for (CashOptionData note : data) {
            portfolio.add(Option.fromData(note, state));
        }

        return portfolio;
    }

    public static OptionPortfolio<Stock> fromStockOptionData(ArrayList<StockOptionData> data, LedgerState state) {
        OptionPortfolio<Stock> portfolio = new OptionPortfolio<>();

        for (StockOptionData note : data) {
            portfolio.add(Option.fromData(note, state));
        }

        return portfolio;
    }

    public ArrayList<StockOptionData> toStockOptionData() {
        ArrayList<StockOptionData> data = new ArrayList<>();

        for (Option<D> a : get()) {
            data.add((StockOptionData) a.toData());
        }

        return data;
    }

    public ArrayList<CashOptionData> toCashOptionData() {
        ArrayList<CashOptionData> data = new ArrayList<>();

        for (Option<D> a : get()) {
            data.add((CashOptionData) a.toData());
        }

        return data;
    }
}
