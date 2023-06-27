package jbs.ledger.classes.banking;

import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.io.types.banking.BankAccountData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

public final class BankAccount implements Account<Cash> {
    public BankAccount(
            UUID uniqueId,
            Economic owner,
            Cash content
    ) {
        this.uniqueId = uniqueId;
        this.owner = owner;
        this.content = content;
        this.minimumBalance = 0d;
    }

    private final UUID uniqueId;
    private final Economic owner;
    private final Cash content;
    private double minimumBalance;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public Economic getOwner() {
        return owner;
    }

    @Override
    public Cash getContent() {
        return content;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double getWithdrawableBalance() {
        double balance = getContent().getBalance();
        return balance - getMinimumBalance();
    }

    // IO
    public BankAccountData toData() {
        BankAccountData data = new BankAccountData();

        data.uniqueId = uniqueId;
        data.owner = owner.getUniqueId();
        data.content = content.toData();
        data.minimumBalance = minimumBalance;

        return data;
    }

    public static BankAccount fromData(BankAccountData data, LedgerState state) {
        return new BankAccount(
                data.uniqueId,
                state.getAssetholder(data.uniqueId),
                Cash.fromData(data.content),
                data.minimumBalance
        );
    }

    private BankAccount(
            UUID uniqueId,
            Economic owner,
            Cash content,
            double minimumBalance
    ) {
        this.uniqueId = uniqueId;
        this.owner = owner;
        this.content = content;
        this.minimumBalance = minimumBalance;
    }
}
