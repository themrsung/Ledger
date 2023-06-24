package jbs.ledger.interfaces.banking;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Unique;

public interface Account<A extends Asset> extends Unique {
    Economic getOwner();
    A getContent();
}
