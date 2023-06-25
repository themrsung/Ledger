package jbs.ledger.classes.invitation;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.organizations.corporate.Board;

public final class DirectorOffer extends Invitation<Person, Board> {
    public DirectorOffer(Board from, Person to) {
        super(from, to);
    }

    @Override
    public void onAccepted() {
        from.addMember(to);
        to.inboundDirectorOffers.remove(this);
    }

    @Override
    public void onDeclined() {
        to.inboundDirectorOffers.remove(this);
    }

    @Override
    public String getDisplayName() {
        return from.getName() + "에서 " + to.getName() + "을 이사로 초대함";
    }
}
