package jbs.ledger.interfaces.corporate;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.NationMember;

import javax.annotation.Nullable;

public interface Corporate extends Business, ShareIssuer, Organization<Person>, NationMember, Symbolic {
    Organization<Person> getBoard();

    @Nullable
    @Override
    default Person getRepresentative() {
        return getBoard().getRepresentative();
    }
}
