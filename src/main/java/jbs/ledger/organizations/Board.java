package jbs.ledger.organizations;

import jbs.ledger.assetholders.person.Person;

import java.util.UUID;

public class Board extends AbstractOrganization<Person> {
    public Board(UUID uniqueId) {
        super(uniqueId);
    }

    public Board(Board copy) {
        super(copy);
    }

    public Board() {
    }
}
