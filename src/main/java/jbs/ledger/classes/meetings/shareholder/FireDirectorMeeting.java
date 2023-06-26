package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class FireDirectorMeeting extends ShareholderMeeting {
    public static FireDirectorMeeting newMeeting(
            Corporate corporation,
            LedgerState state,
            Person directorToFire
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_이사해임_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Assetholder>> voters = new ArrayList<>();
        long votes = 0;

        for (Assetholder a : state.getAssetholders()) {
            Stock stock = a.getStocks().get(corporation.getSymbol());
            if (stock != null) {
                long q = stock.getQuantity();
                if (q > 0) {
                    votes += q;
                    voters.add(new Shareholder(a, q));
                }
            }
        }

        return new FireDirectorMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                directorToFire
        );
    }

    private FireDirectorMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Assetholder>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Person directorToFire
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.directorToFire = directorToFire;
    }

    private final Person directorToFire;

    public Person getDirectorToFire() {
        return directorToFire;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_FIRE_DIRECTOR;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Corporation) {
            Corporation corp = (Corporation) organization;
            corp.getBoard().removeMember(getDirectorToFire());
        }
    }
    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = directorToFire.getUniqueId();

        return data;
    }

    public static FireDirectorMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Assetholder>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new FireDirectorMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                state.getPerson(data.member)
        );
    }
}
