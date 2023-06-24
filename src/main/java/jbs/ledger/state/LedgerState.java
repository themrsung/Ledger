package jbs.ledger.state;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.interfaces.sovereignty.Sovereign;
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

    // Corporations
    public ArrayList<Corporate> getCorporates() {
        ArrayList<Corporate> corporates = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Corporate) {
                corporates.add((Corporate) a);
            }
        }

        return corporates;
    }

    @Nullable
    public Corporate getCorporate(UUID uniqueId) {
        for (Corporate c : getCorporates()) {
            if (c.getUniqueId().equals(uniqueId)) {
                return c;
            }
        }

        return null;
    }

    // Sovereigns
    public ArrayList<Sovereign> getSovereigns() {
        ArrayList<Sovereign> sovereigns = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Sovereign) {
                sovereigns.add((Sovereign) a);
            }
        }

        return sovereigns;
    }

    @Nullable
    public Sovereign getSovereign(UUID uniqueId) {
        for (Sovereign s : getSovereigns()) {
            if (s.getUniqueId().equals(uniqueId)) {
                return s;
            }
        }

        return null;
    }

    // Nations
    public ArrayList<Nation> getNations() {
        ArrayList<Nation> nations = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Nation) {
                nations.add((Nation) a);
            }
        }

        return nations;
    }

    @Nullable
    public Nation getNation(UUID uniqueId) {
        for (Nation n : getNations()) {
            if (n.getUniqueId().equals(uniqueId)) {
                return n;
            }
        }

        return null;
    }

    // Nations
    public ArrayList<NationMember> getNationMembers() {
        ArrayList<NationMember> members = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof NationMember) {
                members.add((NationMember) a);
            }
        }

        return members;
    }

    @Nullable
    public NationMember getNationMember(UUID uniqueid) {
        for (NationMember m : getNationMembers()) {
            if (m.getUniqueId().equals(uniqueid)) {
                return m;
            }
        }

        return null;
    }

    // Foundations
    public ArrayList<Foundation> getFoundations() {
        ArrayList<Foundation> foundations = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Foundation) {
                foundations.add((Foundation) a);
            }
        }

        return foundations;
    }

    @Nullable
    public Foundation getFoundation(UUID uniwueId) {
        for (Foundation f : getFoundations()) {
            if (f.getUniqueId().equals(uniwueId)) {
                return f;
            }
        }

        return null;
    }


}
