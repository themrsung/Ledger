package jbs.ledger.interfaces.organization;

import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.organizations.corporate.Board;

public interface Directorship extends Symbolic {
    Board getBoard();
}
