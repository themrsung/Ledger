package jbs.ledger.io.types.assets.synthetic.stackable;

import jbs.ledger.io.types.assets.basic.StockData;

import java.util.UUID;

public final class StockOptionData extends StackableNoteData {
    public StockOptionData() {
        super();
    }

    public StockData delivery;
    public double exercisePrice;
    public UUID market;
}
