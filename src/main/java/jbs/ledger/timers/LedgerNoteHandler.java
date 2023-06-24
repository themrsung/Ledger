package jbs.ledger.timers;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.StackableNote;

import java.util.Date;

public final class LedgerNoteHandler implements Runnable {
    public LedgerNoteHandler(Ledger ledger) {
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
