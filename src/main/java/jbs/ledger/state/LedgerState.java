package jbs.ledger.state;

import jbs.ledger.classes.Assetholder;
import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.io.types.accounts.AssetholderData;

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
            assetholders.add(Assetholder.getEmptyInstance(data.uniqueId));
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

    private final ArrayList<Assetholder> assetholders;

    // Assetholders

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
}
