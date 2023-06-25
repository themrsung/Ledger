package jbs.ledger.organizations.sovereign;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

public final class Administration extends AbstractOrganization<Person> {
    public Administration(UUID uniqueId) {
        super(uniqueId);
    }

    public Administration(Administration copy) {
        super(copy);

        this.owner = copy.owner;
    }

    public Administration() {
        super();
    }

    @Nullable
    public transient Nation owner = null;

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 행정부";
    }


    public static Administration fromData(OrganizationData data, LedgerState state) {
        Administration board = new Administration(data.uniqueId);

        for (UUID id : data.members) {
            board.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            board.setRepresentative(state.getPerson(data.representative));
        }

        return board;
    }
}
