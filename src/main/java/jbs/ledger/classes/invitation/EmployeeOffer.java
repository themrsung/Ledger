package jbs.ledger.classes.invitation;

import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.person.Person;

public final class EmployeeOffer extends Invitation<Person, Corporation> {
    public EmployeeOffer(Corporation from, Person to) {
        super(from, to);
    }

    @Override
    public void onAccepted() {
        from.addMember(to);
        to.inboundEmployeeOffers.remove(this);
    }

    @Override
    public void onDeclined() {
        to.inboundEmployeeOffers.remove(this);
    }

    @Override
    public String getDisplayName() {
        return from.getName() + "에서 " + to.getName() + "을 직원으로 초대함";
    }
}
