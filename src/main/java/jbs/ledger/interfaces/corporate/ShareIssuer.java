package jbs.ledger.interfaces.corporate;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;

public interface ShareIssuer extends Symbolic {
    long getShareCount();

    Cash getCapital();
    void setCapital(Cash capital);
}
