package jbs.ledger.interfaces.corporate;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.organization.Directorship;
import jbs.ledger.interfaces.organization.Electorate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.organizations.corporate.Board;

import javax.annotation.Nullable;

public interface Corporate extends Business, ShareIssuer, Organization<Person>, Electorate<Assetholder>, NationMember, Symbolic, Directorship {
    long getShareCount();
    void setShareCount(long shares);

    @Nullable
    @Override
    default Person getRepresentative() {
        return getBoard().getRepresentative();
    }
}
