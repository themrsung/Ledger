package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.address.Headquartered;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Symbolic;

import java.util.ArrayList;

public interface Sovereign extends Economic, Headquartered, Symbolic {
    boolean hasAdministrativePower(Person person);
    boolean hasLegislativePower(Person person);
    boolean hasJudicialPower(Person person);
    boolean hasClemency(Person person);

    ArrayList<String> getLaws();
    void addLaw(String law);
    void changeLaw(int index, String law);
    boolean removeLaw(String law);
    void removeLaw(int index);
}
