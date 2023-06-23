package jbs.ledger.types.portfolios.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.types.assets.synthetic.UniqueNote;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;

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
        add(asset.negate());
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getQuantity() == 0L);
    }
}
