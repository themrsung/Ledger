package jbs.ledger.organizations;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.io.types.organizations.OrganizationData;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

public class Board extends AbstractOrganization<Person> {
    public Board(UUID uniqueId) {
        super(uniqueId);
    }

    public Board(Board copy) {
        super(copy);
    }

    public Board() {
        super();
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
