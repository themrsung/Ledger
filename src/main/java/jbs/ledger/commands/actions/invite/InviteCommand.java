package jbs.ledger.commands.actions.invite;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.assetholders.trusts.Trust;
import jbs.ledger.classes.invitation.DirectorOffer;
import jbs.ledger.classes.invitation.EmployeeOffer;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.sovereignty.NationMember;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class InviteCommand extends LedgerPlayerCommand {
    public InviteCommand(Ledger ledger) {
        super(ledger);
    }
    public InviteCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (getActor() instanceof Player) {
            getMessenger().commandOnlyExecutableByOrganizations();
            return;
        }

        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        Assetholder invited = getState().getAssetholder(mainArg, true, true);
        if (invited == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        if (getActor() instanceof Corporation) {
            if (!(invited instanceof Person)) {
                getMessenger().onlyPlayersCanJoinThisOrganization();
                return;
            }

            Corporation inviter = (Corporation) getActor();
            Person ip = (Person) invited;
            EmployeeOffer offer = new EmployeeOffer(
                    inviter,
                    ip
            );

            ip.inboundEmployeeOffers.add(offer);
            getMessenger().inviteSuccessful();
            return;
        } else if (getActor() instanceof Foundation) {
            if (!(invited instanceof Person)) {
                getMessenger().onlyPlayersCanJoinThisOrganization();
                return;
            }

            Foundation inviter = (Foundation) getActor();
            Person ip = (Person) invited;
            DirectorOffer offer = new DirectorOffer(
                    inviter.getBoard(),
                    ip
            );

            ip.inboundDirectorOffers.add(offer);
            getMessenger().inviteSuccessful();
            return;
        } else if (getActor() instanceof Nation) {
            if (!(invited instanceof NationMember)) {
                getMessenger().invitedCannotJoinNations();
                return;
            }

            Nation inviter = (Nation) getActor();
            NationMember nm = (NationMember) invited;
            // TODO offer citizenship
            return;
        } else if (getActor() instanceof Trust) {
            Trust trust = (Trust) getActor();
            trust.setTrustee(invited);
            getMessenger().trusteeSet(invited);
            return;
        } else if (getActor() instanceof Federation) {
            if (!(invited instanceof Nation)) {
                getMessenger().onlyNationsCanJoinThisOrganization();
                return;
            }

            Federation fed = (Federation) getActor();
            Nation n = (Nation) invited;

            // TODO offer membership
            return;
        }

        getMessenger().unknownError();
        return;
    }
}
