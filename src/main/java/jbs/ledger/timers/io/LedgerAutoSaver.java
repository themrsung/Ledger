package jbs.ledger.timers.io;

import jbs.ledger.Ledger;

public final class LedgerAutoSaver implements Runnable {
    public LedgerAutoSaver(Ledger ledger) {
        this.ledger = ledger;
    }
    private final Ledger ledger;
    @Override
    public void run() {
        ledger.save();
    }
}
