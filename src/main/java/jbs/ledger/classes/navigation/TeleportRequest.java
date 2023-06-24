package jbs.ledger.classes.navigation;

import jbs.ledger.assetholders.person.Person;

import java.util.Date;

public class TeleportRequest {
    public TeleportRequest(
            Person from,
            Person to
    ) {
        this.from = from;
        this.to = to;
        this.date = new Date();
    }

    public final Person from;
    public final Person to;
    public final Date date;
}
