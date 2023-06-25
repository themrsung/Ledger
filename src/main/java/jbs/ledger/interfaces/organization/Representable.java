package jbs.ledger.interfaces.organization;

import jbs.ledger.interfaces.common.Unique;

import javax.annotation.Nullable;

public interface Representable<M extends Unique> {

    @Nullable
    M getRepresentative();
    void setRepresentative(@Nullable M representative);
}
