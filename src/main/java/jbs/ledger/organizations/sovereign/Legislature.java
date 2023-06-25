package jbs.ledger.organizations.sovereign;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

public final class Legislature extends AbstractOrganization<Person> {
    public Legislature(UUID uniqueId) {
        super(uniqueId);
    }

    public Legislature(Legislature copy) {
        super(copy);

        this.owner = copy.owner;
    }

    public Legislature() {
        super();
    }

    @Nullable
    public transient Nation owner = null;

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 입법부";
    }


    public static Legislature fromData(OrganizationData data, LedgerState state) {
        Legislature board = new Legislature(data.uniqueId);

        for (UUID id : data.members) {
            board.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            board.setRepresentative(state.getPerson(data.representative));
        }

        return board;
    }
}
