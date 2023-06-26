package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.organizations.sovereign.Administration;
import jbs.ledger.organizations.sovereign.Judiciary;
import jbs.ledger.organizations.sovereign.Legislature;

public interface Tripartite {
    Administration getAdministration();
    Legislature getLegislature();
    Judiciary getJudiciary();
}