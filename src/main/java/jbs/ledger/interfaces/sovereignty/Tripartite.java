package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.organizations.sovereign.Administration;
import jbs.ledger.organizations.sovereign.Judiciary;
import jbs.ledger.organizations.sovereign.Legislature;

import java.util.Date;

public interface Tripartite extends Sovereign {
    Administration getAdministration();
    Legislature getLegislature();
    Judiciary getJudiciary();
}
