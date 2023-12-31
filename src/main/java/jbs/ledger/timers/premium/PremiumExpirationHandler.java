package jbs.ledger.timers.premium;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.person.Person;

import java.util.Date;

public final class PremiumExpirationHandler implements Runnable {
    public PremiumExpirationHandler(Ledger ledger) {
        this.ledger = ledger;
    }

    private final Ledger ledger;

    @Override
    public void run() {
        for (Person p : ledger.getState().getPeople()) {
            Date now = new Date();
            Date expiry = p.getPremiumExpiration();

            if (expiry != null) {
                if (expiry.before(now)) {
                    p.setPremium(false);
                    p.setPremiumExpiration(null);
                } else {
                    p.setPremium(true);
                }
            }
        }
    }
}
