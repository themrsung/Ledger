package jbs.ledger.assetholders.sovereignties.nations;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.interfaces.sovereignty.Tripartite;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PresidentialRepublicData;
import jbs.ledger.organizations.sovereign.Administration;
import jbs.ledger.organizations.sovereign.Judiciary;
import jbs.ledger.organizations.sovereign.Legislature;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Presidential Republics have a president and vice president.
 */
public final class PresidentialRepublic extends Nation implements Tripartite {
    public PresidentialRepublic(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name, symbol);

        this.administration = new Administration();
        this.legislature = new Legislature();
        this.judiciary = new Judiciary();
    }

    public PresidentialRepublic(PresidentialRepublic copy) {
        super(copy);

        this.administration = copy.administration;
        this.legislature = copy.legislature;
        this.judiciary = copy.judiciary;
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.PRESIDENTIAL_REPUBLIC;
    }

    @Override
    public long getProtectionRadius() {
        return 1500;
    }

    // Tripartite
    private Administration administration;
    private Legislature legislature;
    private Judiciary judiciary;

    @Override
    public Administration getAdministration() {
        return administration;
    }

    @Override
    public Legislature getLegislature() {
        return legislature;
    }

    @Override
    public Judiciary getJudiciary() {
        return judiciary;
    }

    @Override
    public boolean hasAdministrativePower(Person person) {
        return getAdministration().getMembers().contains(person);
    }

    @Override
    public boolean hasLegislativePower(Person person) {
        return getLegislature().getMembers().contains(person);
    }

    @Override
    public boolean hasJudicialPower(Person person) {
        return getJudiciary().getMembers().contains(person);
    }

    @Override
    public boolean hasClemency(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    @Nullable
    @Override
    public NationMember getRepresentative() {
        return getAdministration().getRepresentative();
    }

    // IO

    @Override
    public PresidentialRepublicData toData() {
        PresidentialRepublicData data = new PresidentialRepublicData(super.toData());

        data.administration = administration.toData();
        data.legislature = legislature.toData();
        data.judiciary = judiciary.toData();

        return data;
    }

    public static PresidentialRepublic getEmptyInstance(UUID uniqueId) {
        return new PresidentialRepublic(uniqueId);
    }

    private PresidentialRepublic(UUID uniqueId) {
        super(uniqueId);

        this.administration = new Administration();
        this.legislature = new Legislature();
        this.judiciary = new Judiciary();
    }

    public void load(PresidentialRepublicData data, LedgerState state) {
        super.load(data, state);

        this.administration = Administration.fromData(data.administration, state);
        this.legislature = Legislature.fromData(data.administration, state);
        this.judiciary = Judiciary.fromData(data.administration, state);
    }
}
