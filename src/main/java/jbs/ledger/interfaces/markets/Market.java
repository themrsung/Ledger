package jbs.ledger.interfaces.markets;

import jbs.ledger.types.assets.Cash;

public interface Market<A> {
    String getCurrency();
    double getUnitPrice(A asset);
    double getValue(A asset);
}
