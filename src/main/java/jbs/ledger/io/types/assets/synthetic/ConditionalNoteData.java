package jbs.ledger.io.types.assets.synthetic;

import jbs.ledger.io.types.assets.AssetData;
import jbs.ledger.io.types.conditions.OptionExerciseConditionData;

public final class ConditionalNoteData<D extends AssetData> extends NoteData<D> {
    public ConditionalNoteData() {
        super();
    }

    public OptionExerciseConditionData condition;
}
