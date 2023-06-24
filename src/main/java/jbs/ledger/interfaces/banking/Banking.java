package jbs.ledger.interfaces.banking;

import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Provides code for banking.
 * @param <A> Type of asset to bank.
 */
public interface Banking<A extends Asset> {
    ArrayList<Account<A>> getAccounts();

    default ArrayList<Account<A>> getAccounts(Economic owner) {
        ArrayList<Account<A>> accounts = new ArrayList<>();

        for (Account<A> account : getAccounts()) {
            if (account.getOwner().equals(owner)) {
                accounts.add(account);
            }
        }

        return accounts;
    }
    @Nullable
    default Account<A> getAccount(UUID uniqueId) {
        for (Account<A> account : getAccounts()) {
            if (account.getUniqueId().equals(uniqueId)) {
                return account;
            }
        }

        return null;
    }

    void addAccount(Account<A> account);
    boolean removeAccount(Account<A> account);
}
