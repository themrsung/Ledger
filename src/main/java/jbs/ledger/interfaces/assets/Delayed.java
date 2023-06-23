package jbs.ledger.interfaces.assets;

import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * A thing with delayed delivery. Used with assets to create notes.
 * @param <D> The thing to deliver on expiration.
 */
public interface Delayed<D extends Asset> {
    D getDelivery();

    @Nullable
    Date getExpiration();
    Economic getDeliverer();
}
