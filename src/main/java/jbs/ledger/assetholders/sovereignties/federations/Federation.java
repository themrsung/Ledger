package jbs.ledger.assetholders.sovereignties.federations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.assetholders.sovereignties.federations.FederationData;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public final class Federation extends Assetholder implements Sovereign, Organization<Nation> {
    public Federation(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.members = new ArrayList<>();
        this.capital = null;
    }

    public Federation(Assetholder copy) {
        super(copy);

        this.members = new ArrayList<>();
        this.capital = null;
    }

    private final ArrayList<Nation> members;
    @Nullable
    private Nation capital;

    @Override
    public ArrayList<Nation> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(Nation member) {
        members.add(member);
    }

    @Override
    public boolean removeMember(Nation member) {
        return members.remove(member);
    }

    @Nullable
    @Override
    public Nation getRepresentative() {
        return capital;
    }

    @Override
    public void setRepresentative(@Nullable Nation representative) {
        this.capital = representative;
    }
    @Override
    public AssetholderType getType() {
        return AssetholderType.FEDERATION;
    }

    // IO

    @Override
    public FederationData toData() {
        FederationData data = new FederationData(super.toData());

        return data;
    }

    public static Federation getEmptyInstance(UUID uniqueId) {
        return new Federation(uniqueId);
    }

    private Federation(UUID uniqueId) {
        super(uniqueId);

        this.members = new ArrayList<>();
    }

    public void load(FederationData data, LedgerState state) {
        super.load(data, state);

        for (UUID n : data.members) {
            members.add(state.getNation(n));
        }
    }
}
