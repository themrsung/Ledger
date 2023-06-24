package jbs.ledger.classes.messages;

import jbs.ledger.Ledger;
import jbs.ledger.state.LedgerState;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class DirectMessageProcessor implements Runnable {
    public DirectMessageProcessor(Ledger ledger) {
        this.state = ledger.getState();
    }
    private final LedgerState state;

    public void run() {
        for (DirectMessage dm : state.getMessages()) {
            Player r = dm.recipient.toPlayer();
            if (r != null && !dm.shown) {
                String formatted = dm.sender != null ? "[" + dm.sender.getName() + " -> 본인] " + dm.content : "[!] " + dm.content;
                r.sendRawMessage(formatted);
                dm.shown = true;
            }

            long diff = Math.abs(new Date().getTime() - dm.date.getTime());
            long minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

            if (minutes > 30) {
                state.removeMessage(dm);
            }
        }
    }
}
