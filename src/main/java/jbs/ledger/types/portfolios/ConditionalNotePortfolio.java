package jbs.ledger.types.portfolios;

import jbs.ledger.types.assets.ConditionalNote;

import javax.annotation.Nullable;

public final class ConditionalNotePortfolio<D> extends AbstractPortfolio<ConditionalNote<D>> {
    public ConditionalNotePortfolio(ConditionalNotePortfolio<D> copy) {
        super(copy);
    }
    public ConditionalNotePortfolio() {
        super();
    }

    @Nullable
    @Override
    public ConditionalNote<D> get(String symbol) {
        for (ConditionalNote<D> note : get()) {
            if (note.getSymbol().equalsIgnoreCase(symbol) ) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void add(ConditionalNote<D> asset) {
        ConditionalNote<D> existing = get(asset.getSymbol());

        if (existing != null && existing.isStackable(asset)) {
            existing.addQuantity(asset.getQuantity());
            return;
        }

        super.add(asset);

        clean();
    }

    @Override
    public void remove(ConditionalNote<D> asset) {
        add(asset.negate());
    }

    @Override
    public void clean() {
        getRaw().removeIf(e -> e.getQuantity() == 0);
    }
}
