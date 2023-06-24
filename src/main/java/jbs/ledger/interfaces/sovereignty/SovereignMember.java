package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.interfaces.common.Unique;

import javax.annotation.Nullable;

public interface SovereignMember extends Unique {
    @Nullable
    Sovereign getNationality();

    void setNationality(@Nullable Sovereign nation);
}
