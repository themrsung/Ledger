package jbs.ledger.assetholders.person;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.trusts.RealEstateTrust;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.person.PersonData;
import jbs.ledger.io.types.assetholders.trusts.RealEstateTrustData;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Players
 */
public final class Person extends Assetholder implements NationMember {
    public Person(UUID uniqueId, String name) {
        super(uniqueId, name);
    }
    public Person(Assetholder copy) {
        super(copy);
    }

    // Type
    @Override
    public AssetholderType getType() {
        return AssetholderType.PERSON;
    }

    // IO
    public static Person getEmptyInstance(UUID uniqueId) {
        return new Person(uniqueId);
    }
    private Person(UUID uniqueId) {
        super(uniqueId);
    }

    @Override
    public PersonData toData() {
        PersonData data = new PersonData(super.toData());

        return data;
    }

    public void load(PersonData data, LedgerState state) {
        super.load(data, state);
    }

}
