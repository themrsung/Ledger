package jbs.ledger.types.portfolios.synthetic;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.portfolios.AbstractPortfolio;

import javax.annotation.Nullable;

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
}
