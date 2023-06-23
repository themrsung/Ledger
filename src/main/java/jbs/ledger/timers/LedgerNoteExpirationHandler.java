package jbs.ledger.timers;

import jbs.ledger.Ledger;
import jbs.ledger.classes.Assetholder;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.Date;

public final class LedgerNoteExpirationHandler implements Runnable {
    public LedgerNoteExpirationHandler(Ledger ledger) {
        this.state = ledger.getState();
    }
    private final LedgerState state;

    public void run() {
        Date now = new Date();

        for (Assetholder holder : state.getAssetholders()) {
            for (StackableNote<Cash> bond : holder.getBonds().get()) {
                Date expiry = bond.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    bond.onExpired(holder);
                    holder.getBonds().remove(bond);
                }
            }
        }
    }
}
