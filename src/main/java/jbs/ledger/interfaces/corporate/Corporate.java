package jbs.ledger.interfaces.corporate;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.organization.Organization;

import javax.annotation.Nullable;

public interface Corporate extends Business, ShareIssuer, Organization<Person> {
    Organization<Person> getBoard();

    @Nullable
    @Override
    default Person getRepresentative() {
        return getBoard().getRepresentative();
    }
}
