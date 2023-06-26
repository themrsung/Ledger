package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.address.Headquartered;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Symbolic;

public interface Sovereign extends Economic, Headquartered, Symbolic {
    boolean hasAdministrativePower(Person person);
    boolean hasLegislativePower(Person person);
    boolean hasJudicialPower(Person person);
    boolean hasClemency(Person person);
}
