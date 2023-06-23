package jbs.ledger.timers;

import jbs.ledger.Ledger;

public final class LedgerAutoSaver implements Runnable {
    public LedgerAutoSaver(Ledger ledger) {
        this.ledger = ledger;
    }
    private final Ledger ledger;
    public void run() {
        ledger.save();
    }
}
