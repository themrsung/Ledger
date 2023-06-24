package jbs.ledger.assetholders.person;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.interfaces.common.Unique;

import java.util.UUID;

public final class Person extends Assetholder {
    public Person(UUID uniqueId, String name) {
        super(uniqueId, name);
    }
    public Person(Assetholder copy) {
        super(copy);
    }



    // IO
    public static Person getEmptyInstance(UUID uniqueId) {
        return new Person(uniqueId);
    }
    private Person(UUID uniqueId) {
        super(uniqueId);
    }

    // Type
    @Override
    public AssetholderType getType() {
        return AssetholderType.PERSON;
    }
}
