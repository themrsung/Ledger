package jbs.ledger.interfaces.assets;

import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;
import java.util.Date;

public interface Delayed<D> {
    D getDelivery();

    @Nullable
    Date getExpiration();
    Economic getDeliverer();
}
