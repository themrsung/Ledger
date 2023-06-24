package jbs.ledger.assetholders.person;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.SovereignMember;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Players
 */
public final class Person extends Assetholder implements SovereignMember {
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

    @Nullable
    @Override
    public Sovereign getNationality(LedgerState state) {
        return null;
    }
}
