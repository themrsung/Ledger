package jbs.ledger.assetholders.corporations.finance;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.banking.BankAccount;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.banking.Banking;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A bank holds cash on their clients' behalf and pays interest.
 * Interest rates can be negative.
 * Line of credit can be offered via negative account balances.
 */
public final class Bank extends Corporation implements Banking<Cash> {
    public Bank(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital
    ) {
        super(uniqueId, name, symbol, currency, capital);

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
}
