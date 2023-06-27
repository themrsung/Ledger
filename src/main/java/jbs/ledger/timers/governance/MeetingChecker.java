package jbs.ledger.timers.governance;

import jbs.ledger.Ledger;
import jbs.ledger.interfaces.organization.Electorate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.interfaces.organization.Organization;

public final class MeetingChecker implements Runnable {
    public MeetingChecker(Ledger ledger) {
        this.ledger = ledger;
    }

    private final Ledger ledger;

    @Override
    public void run() {
        for (Electorate<?> e : ledger.getState().getElectorates()) {
            for (Meeting<?> m : e.getOpenMeetings()) {
                if (m.hasPassed()) {
                    m.onPassed((Organization<?>) e, ledger.getState());
                    e.removeOpenMeeting(m);
                } else if (m.hasFailed()) {
                    e.removeOpenMeeting(m);
                }
            }
        }
    }
}
