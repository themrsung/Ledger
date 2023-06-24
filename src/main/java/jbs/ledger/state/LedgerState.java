package jbs.ledger.state;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.io.types.assetholders.AssetholderData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The running state of this plugin.
 */
public final class LedgerState {
    public LedgerState(LedgerSaveState saveState) {
        assetholders = new ArrayList<>();

        for (AssetholderData data : saveState.assetholders) {
            // FIXME asjdlksajkd
//            assetholders.add(Assetholder.getEmptyInstance(data.uniqueId));
        }

        for (Assetholder a : assetholders) {
            for (AssetholderData ad : saveState.assetholders) {
                if (a.getUniqueId().equals(ad.uniqueId)) {
                    a.load(ad, this);
                }
            }
        }
    }

    public LedgerState() {
        this.assetholders = new ArrayList<>();
    }

    // Assetholders
    private final ArrayList<Assetholder> assetholders;

    /**
     * Gets all assetholders in existence.
     * @return Returns a copied list of assetholders.
     */
    public ArrayList<Assetholder> getAssetholders() {
        return new ArrayList<>(assetholders);
    }

    @Nullable
    public Assetholder getAssetholder(UUID holderId) {
        for (Assetholder a : getAssetholders()) {
            if (a.getUniqueId().equals(holderId)) {
                return a;
            }
        }

        return null;
    }

    public void addAssetholder(Assetholder assetholder) {
        this.assetholders.add(assetholder);
    }
    public boolean removeAssetholder(Assetholder assetholder) {
        return this.assetholders.remove(assetholder);
    }

    // People
    public ArrayList<Person> getPeople() {
        ArrayList<Person> people = new ArrayList<>();;

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Person) {
                people.add((Person) a);
            }
        }

        return people;
    }
    @Nullable
    public Person getPerson(UUID uniqueId) {
        for (Person p : getPeople()) {
            if (p.getUniqueId().equals(uniqueId)) {
                return p;
            }
        }

        return null;
    }

}
