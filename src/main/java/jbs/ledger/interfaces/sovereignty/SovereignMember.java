package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.state.LedgerState;

import javax.annotation.Nullable;

public interface SovereignMember extends Unique {
    @Nullable
    Sovereign getNationality(LedgerState state);
}
