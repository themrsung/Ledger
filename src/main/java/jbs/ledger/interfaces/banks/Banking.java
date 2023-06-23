package jbs.ledger.interfaces.banks;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Unique;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A bank that can hold clients' assets.
 * Supports one account per client.
 * @param <A> Type of asset to hold
 */
public interface Banking<A extends Asset> extends Unique {
    ArrayList<ClientAccount<A>> getAccounts();

    @Nullable
    default ClientAccount<A> getAccountById(UUID accountId) {
        for (ClientAccount<A> a : getAccounts()) {
            if (a.getUniqueId().equals(accountId)) {
                return a;
            }
        }

        return null;
    }

    @Nullable
    default ClientAccount<A> getAccountByClientId(UUID clientId) {
        for (ClientAccount<A> a : getAccounts()) {
            if (a.getClientId().equals(clientId)) {
                return a;
            }
        }

        return null;
    }
}
