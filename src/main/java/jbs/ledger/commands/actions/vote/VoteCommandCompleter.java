package jbs.ledger.commands.actions.vote;

import jbs.ledger.Ledger;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.interfaces.organization.Meeting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class VoteCommandCompleter extends LedgerCommandAutoCompleter {
    public VoteCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            for (Meeting<?> meeting : getState().getMeetings()) {
                for (VotableMember<?> vm : meeting.getVotableMembers()) {
                    if (vm.getMember().equals(getActor())) {
                        list.add(meeting.getSymbol());
                    }
                }
            }
        } else if (args.length < 3) {
            list.addAll(LedgerCommandKeywords.ACCEPT);
            list.addAll(LedgerCommandKeywords.DENY);
        }

        return list;
    }
}
