package jbs.ledger.interfaces.organization;

import jbs.ledger.interfaces.common.Unique;

import java.util.ArrayList;

public interface Electorate<V extends Unique> extends Unique {
    ArrayList<Meeting<V>> getOpenMeetings();
    void addOpenMeeting(Meeting<V> meeting);
    boolean removeOpenMeeting(Meeting<?> meeting);
}
