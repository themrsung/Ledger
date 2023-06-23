package jbs.ledger.types.portfolios;

import jbs.ledger.types.assets.StackableNote;

import javax.annotation.Nullable;

public final class StackableNotePortfolio<D> extends AbstractPortfolio<StackableNote<D>> {
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
        StackableNote<D> existing = get(asset.getSymbol());

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
