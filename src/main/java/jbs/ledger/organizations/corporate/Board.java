package jbs.ledger.organizations.corporate;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.organizations.AbstractOrganization;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;
import java.util.UUID;

public final class Board extends AbstractOrganization<Person> {
    public Board(UUID uniqueId) {
        super(uniqueId);
    }

    public Board(Board copy) {
        super(copy);

        this.owner = copy.owner;
    }

    public Board() {
        super();
    }

    @Nullable
    public transient Assetholder owner = null;

    public String getName() {
        if (owner == null) return "알 수 없음";

        return owner.getName() + " 이사회";
    }


    public static Board fromData(OrganizationData data, LedgerState state) {
        Board board = new Board(data.uniqueId);

        for (UUID id : data.members) {
            board.addMember(state.getPerson(id));
        }

        if (data.representative != null) {
            board.setRepresentative(state.getPerson(data.representative));
        }

        return board;
    }
}
