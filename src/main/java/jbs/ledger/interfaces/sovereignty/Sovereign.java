package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.address.Headquartered;
import jbs.ledger.interfaces.common.Economic;

public interface Sovereign extends Economic, Headquartered {
    boolean hasAdministrativePower(Person person);
    boolean hasLegislativePower(Person person);
    boolean hasJudicialPower(Person person);
    boolean hasClemency(Person person);
}
