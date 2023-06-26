package jbs.ledger.commands.actions.vote;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.organization.Meeting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class VoteCommand extends LedgerPlayerCommand {
    public VoteCommand(Ledger ledger) {
        super(ledger);
    }
    public VoteCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 1) {
            getMessenger().insufficientArgs();
            return;
        }

        Meeting<?> meeting = getState().getMeeting(mainArg);
        if (meeting == null) {
            getMessenger().meetingNotFound();
            return;
        }

        VotableMember<?> vm = meeting.getVotableMember(getActor().getUniqueId());
        if (vm == null) {
            getMessenger().noVotesRemaining();
            return;
        }

        boolean yes;
        if (LedgerCommandKeywords.ACCEPT.contains(argsAfterMain[0])) {
            yes = true;
        } else if (LedgerCommandKeywords.DENY.contains(argsAfterMain[0])) {
            yes = false;
        } else {
            getMessenger().invalidKeyword();
            return;
        }

        if (!meeting.vote(vm, yes)) {
            getMessenger().unknownError();
            return;
        }

        getMessenger().votesCast();
    }
}
