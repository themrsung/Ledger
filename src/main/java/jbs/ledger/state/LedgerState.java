package jbs.ledger.state;

import jbs.ledger.classes.Account;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.banks.Banking;
import jbs.ledger.interfaces.banks.ClientAccount;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.interfaces.portfolios.Portfolio;
import jbs.ledger.types.portfolios.UniqueNotePortfolio;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The running state of this plugin.
 */
public class LedgerState {
    public LedgerState() {
        this.accounts = new ArrayList<>();
        this.markets = new ArrayList<>();
        this.banks = new ArrayList<>();
    }

    private final ArrayList<Account> accounts;
    private final ArrayList<Market<? extends Asset>> markets;
    private final ArrayList<Banking<? extends Asset>> banks;

    // Accounts

    /**
     * Gets all accounts in existence.
     * @return Returns a copied list of accounts.
     */
    public ArrayList<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Nullable
    public Account getAccountById(UUID accountId) {
        for (Account a : getAccounts()) {
            if (a.getUniqueId().equals(accountId)) {
                return a;
            }
        }

        return null;
    }

    @Nullable
    public Account getAccountByOwnerId(UUID ownerId) {
        for (Account a : getAccounts()) {
            if (a.getOwnerId().equals(ownerId)) {
                return a;
            }
        }

        return null;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean removeAccount(Account account) {
        return this.accounts.remove(account);
    }

    // Markets

    /**
     * Gets all markets in existence.
     * @return Returns a copied list of markets.
     */
    public ArrayList<Market<? extends Asset>> getMarkets() {
        return new ArrayList<>(markets);
    }

    @Nullable
    public Market<?> getMarket(UUID marketId) {
        for (Market<?> m : getMarkets()) {
            if (m.getUniqueId().equals(marketId)) {
                return m;
            }
        }
        return null;
    }

    public void addMarket(Market<? extends Asset> market) {
        this.markets.add(market);
    }

    public boolean removeMarket(Market<? extends Asset> market) {
        return this.markets.remove(market);
    }

    // Banks

    /**
     * Gets all banks in existence.
     * @return Returns a copied list of banks.
     */
    public ArrayList<Banking<? extends Asset>> getBanks() {
        return new ArrayList<>(banks);
    }

    @Nullable
    public Banking<?> getBank(UUID bankId) {
        for (Banking<?> b : getBanks()) {
            if (b.getUniqueId().equals(bankId)) {
                return b;
            }
        }

        return null;
    }

    public void addBank(Banking<? extends Asset> bank) {
        this.banks.add(bank);
    }

    public boolean removeBank(Banking<? extends Asset> bank) {
        return this.banks.remove(bank);
    }

    // Interface getters

    /**
     * Gets everything that is unique. (Accounts, Markets, Orders, etc.)
     * @return Returns a copied list of uniques.
     */
    public ArrayList<Unique> getUniques() {
        ArrayList<Unique> uniques = new ArrayList<>();

        ArrayList<Account> accounts = getAccounts();
        ArrayList<Market<? extends Asset>> markets = getMarkets();
        ArrayList<Banking<? extends Asset>> banks = getBanks();

        uniques.addAll(accounts);
        uniques.addAll(markets);
        uniques.addAll(banks);

        for (Account a : accounts) {
            uniques.addAll(a.getNotes().get());
            uniques.addAll(a.getCommodityForwards().get());
            uniques.addAll(a.getStockForwards().get());
        }

        for (Market<?> m : markets) {
            uniques.addAll(m.getOrders());
        }

        for (Banking<?> b : banks) {
            ArrayList<? extends ClientAccount<?>> clientAccounts = b.getAccounts();
            uniques.addAll(clientAccounts);

            for (ClientAccount<?> ca : clientAccounts) {
                Portfolio<?> p = ca.getContent();

                if (p instanceof UniqueNotePortfolio<?>) {
                    uniques.addAll(((UniqueNotePortfolio<?>) p).get());
                }
            }

        }

        return uniques;
    }
}
