package jbs.ledger.organizations.sovereign;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

public final class Judiciary extends AbstractOrganization<Person> {
    public Judiciary(UUID uniqueId) {
        super(uniqueId);
    }

    public Judiciary(Judiciary copy) {
        super(copy);

        this.owner = copy.owner;
    }

    public Judiciary() {
        super();
    }

    @Nullable
    public transient Nation owner = null;

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 사법부";
    }


    public static Judiciary fromData(OrganizationData data, LedgerState state) {
        Judiciary board = new Judiciary(data.uniqueId);

        for (UUID id : data.members) {
            board.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            board.setRepresentative(state.getPerson(data.representative));
        }

        return board;
    }
}
