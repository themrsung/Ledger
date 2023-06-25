package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.banking.BankAccount;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.banking.Banking;
import jbs.ledger.io.types.assetholders.corporations.finance.BankData;
import jbs.ledger.io.types.banking.BankAccountData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A bank holds cash on their clients' behalf and pays interest.
 * Banks can also issue debit cards.
 * Interest rates can be negative.
 * Line of credit can be offered via negative account balances.
 */
public final class Bank extends Corporation implements Banking<Cash> {
    public Bank(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital,
            long shareCount
    ) {
        super(uniqueId, name, symbol, currency, capital, shareCount);

        this.accounts = new ArrayList<>();
        this.interestRate = 0f;
    }

    public Bank(Bank copy) {
        super(copy);

        this.accounts = copy.accounts;
        this.interestRate = copy.interestRate;
    }

    private final ArrayList<Account<Cash>> accounts;
    private float interestRate;

    @Override
    public ArrayList<Account<Cash>> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void addAccount(Account<Cash> account) {
        accounts.add(account);
    }

    @Override
    public boolean removeAccount(Account<Cash> account) {
        return accounts.remove(account);
    }

    public float getInterestRate() {
        return interestRate;
    }

    public float getDailyInterestRate() {
        return (float) (Math.pow(getInterestRate() + 1, (double) 1 / 365) - 1);
    }

    public float getHourlyInterestRate() {
        return (float) (Math.pow(getDailyInterestRate() + 1, (double) 1 / 24) - 1);
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public void payHourlyInterest() {
        for (Account<Cash> account : getAccounts()) {
            String currency = account.getContent().getSymbol();
            double balance = account.getContent().getBalance();

            Cash interest = new Cash(currency, balance * getHourlyInterestRate());

            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    this,
                    account.getOwner(),
                    interest,
                    "Bank interest payment"
            ));
        }
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.BANK;
    }

    // IO

    @Override
    public BankData toData() {
        BankData data = new BankData(super.toData());

        for (Account<Cash> a : getAccounts()) {
            BankAccount ba = (BankAccount) a;
            data.accounts.add(ba.toData());
        }

        data.interestRate = interestRate;

        return data;
    }

    public static Bank getEmptyInstance(UUID uniqueId) {
        return new Bank(uniqueId);
    }

    private Bank(UUID uniqueId) {
        super(uniqueId);

        this.accounts = new ArrayList<>();
    }

    public void load(BankData data, LedgerState state) {
        super.load(data, state);

        this.accounts.clear();

        for (BankAccountData bad : data.accounts) {
            this.accounts.add(BankAccount.fromData(bad, state));
        }

        this.interestRate = data.interestRate;
    }
}
