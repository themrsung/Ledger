package jbs.ledger.timers.governance;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.ParliamentaryRepublic;
import jbs.ledger.assetholders.sovereignties.nations.PresidentialRepublic;
import jbs.ledger.interfaces.sovereignty.Sovereign;

import java.util.Date;

public class OfficeTermKeeper implements Runnable {
    public OfficeTermKeeper(Ledger ledger) {
        this.ledger = ledger;
    }
    private final Ledger ledger;

    @Override
    public void run() {
        for (Sovereign s : ledger.getState().getSovereigns()) {
            if (s instanceof PresidentialRepublic) {
                PresidentialRepublic republic = (PresidentialRepublic) s;

                if (republic.getLastPresidentialElectionDate() == null) {
                    republic.setLastPresidentialElectionDate(new Date());
                }

                if (republic.getLastSenateResetDate() == null) {
                    republic.setLastSenateResetDate(new Date());
                }

                Date now = new Date();

                if (republic.getLastPresidentialElectionDate().before(now)) {
                    republic.setRepresentative(null);
                }

                if (republic.getLastSenateResetDate().before(now)) {
                    for (Person senator : republic.getLegislature().getMembers()) {
                        republic.getLegislature().removeMember(senator);
                    }

                    republic.setLastSenateResetDate(new Date());
                }

            } else if (s instanceof ParliamentaryRepublic) {
                ParliamentaryRepublic republic = (ParliamentaryRepublic) s;

                if (republic.getLastParliamentResetDate() == null) {
                    republic.setLastParliamentResetDate(new Date());
                }

                Date now = new Date();

                if (republic.getLastParliamentResetDate().before(now)) {
                    for (Person mp : republic.getLegislature().getMembers()) {
                        republic.getLegislature().removeMember(mp);
                    }

                    republic.setLastParliamentResetDate(new Date());
                }
            }
        }
    }
}
