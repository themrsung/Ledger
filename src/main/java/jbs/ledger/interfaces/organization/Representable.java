package jbs.ledger.interfaces.organization;

import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.common.Unique;

import javax.annotation.Nullable;

public interface Representable<M extends Unique> extends Symbolic {

    @Nullable
    M getRepresentative();
    void setRepresentative(@Nullable M representative);
}
