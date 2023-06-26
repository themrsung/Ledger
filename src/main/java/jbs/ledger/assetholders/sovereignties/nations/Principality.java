package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PresidentialRepublicData;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PrincipalityData;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Principalities have a monarch.
 */
public final class Principality extends Nation {
    public Principality(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name, symbol);
    }

    public Principality(Principality copy) {
        super(copy);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRINCIPALITY;
    }

    // Ownership
    @Nullable
    @Override
    public Person getRepresentative() {
        return (Person) super.getRepresentative();
    }

    @Override
    public boolean hasAdministrativePower(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    @Override
    public boolean hasLegislativePower(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    @Override
    public boolean hasJudicialPower(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    @Override
    public boolean hasClemency(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    // Protection

    @Override
    public long getProtectionRadius() {
        return 5000;
    }


    // IO

    @Override
    public PrincipalityData toData() {
        PrincipalityData data = new PrincipalityData(super.toData());

        return data;
    }

    public static Principality getEmptyInstance(UUID uniqueId) {
        return new Principality(uniqueId);
    }

    private Principality(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(PrincipalityData data, LedgerState state) {
        super.load(data, state);
    }

}
