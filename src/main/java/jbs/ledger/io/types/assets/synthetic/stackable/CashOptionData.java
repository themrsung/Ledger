package jbs.ledger.io.types.assets.synthetic.stackable;

import jbs.ledger.io.types.assets.basic.CashData;
import jbs.ledger.types.assets.synthetic.OptionType;

import java.util.UUID;

public final class CashOptionData extends StackableNoteData {
    public CashOptionData() {
        super();
    }

    public CashData delivery;
    public OptionType optionType;
    public double exercisePrice;
    public UUID market;
}
