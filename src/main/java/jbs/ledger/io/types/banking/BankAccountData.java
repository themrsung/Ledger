package jbs.ledger.io.types.banking;

import jbs.ledger.io.types.assets.basic.CashData;

import java.util.UUID;

public class BankAccountData {
    public BankAccountData() {}

    public UUID uniqueId;
    public UUID owner;
    public CashData content;
    public double minimumBalance;
}
