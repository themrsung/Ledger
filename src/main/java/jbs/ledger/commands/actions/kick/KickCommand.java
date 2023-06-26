package jbs.ledger.commands.actions.kick;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Trusteeship;
import jbs.ledger.interfaces.sovereignty.NationMember;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class KickCommand extends LedgerPlayerCommand {
    public KickCommand(Ledger ledger) {
        super(ledger);
    }
    public KickCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        Assetholder a = getState().getAssetholder(mainArg, true, true);
        if (a == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        if (getActor() instanceof Corporate) {
            Corporate c = (Corporate) getActor();
            if (!(a instanceof Person)) {
                getMessenger().memberNotInOrganization();
                return;
            }

            Person p = (Person) a;

            if (!c.getMembers().contains(p) && !c.getBoard().getMembers().contains(p)) {
                getMessenger().memberNotInOrganization();
                return;
            }

            if (c.getBoard().getMembers().contains(p)) {
                c.getBoard().removeMember(p);
                c.getMembers().remove(p);

                getMessenger().memberKickedFromOrganization();
                return;
            }

            if (c.getMembers().contains(p)) {
                c.removeMember(p);
                getMessenger().memberKickedFromOrganization();
                return;
            }
        } else if (getActor() instanceof Trusteeship) {
            Trusteeship t = (Trusteeship) getActor();

            if (t.getBeneficiary() != null) {
                if (t.getBeneficiary().equals(a)) {
                    t.setBeneficiary(null);
                    getMessenger().memberKickedFromOrganization();
                    return;
                }
            }

            if (t.getTrustee() != null) {
                if (t.getTrustee().equals(a)) {
                    t.setTrustee(null);
                    getMessenger().memberKickedFromOrganization();
                    return;
                }
            }

            getMessenger().memberNotInOrganization();
            return;
        } else if (getActor() instanceof Nation) {
            Nation n = (Nation) getActor();

            if (!(a instanceof NationMember)) {
                getMessenger().memberCannotJoinThisOrganization();
                return;
            }

            if (!n.getMembers().contains(a)) {
                getMessenger().memberNotInOrganization();
                return;
            }

            NationMember nm = (NationMember) a;

            n.removeMember(nm);
            getMessenger().memberKickedFromOrganization();
            return;
        }

        getMessenger().commandOnlyExecutableByOrganizations();
        return;
    }
}
