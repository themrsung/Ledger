package jbs.ledger.commands.actions.punish;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.special.SovereignCorporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public final class PunishCommand extends LedgerPlayerCommand {
    public PunishCommand(Ledger ledger) {
        super(ledger);
    }
    public PunishCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length  < 1) {
            getMessenger().insufficientArgs();
            return;
        }

        if (!(getActor() instanceof Nation)) {
            getMessenger().commandOnlyExecutableByNations();
            return;
        }

        Person p = getState().getPerson(argsAfterMain[0]);
        if (p == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        Sovereign s = (Sovereign) getActor();
        ArrayList<Person> citizens = new ArrayList<>();

        if (s instanceof Nation) {
            citizens.addAll(((Nation) s).getCitizens());
        } else if (s instanceof SovereignCorporation) {
            citizens.addAll(((SovereignCorporation) s).getMembers());
        } else {
            getMessenger().commandOnlyExecutableByNations();
            return;
        }

        if (!citizens.contains(p)) {
            getMessenger().playerNotCitizen();
            return;
        }

        if (LedgerCommandKeywords.BAN.contains(mainArg)) {
            if (s.getBannedPlayers().contains(p)) {
                getMessenger().playerAlreadyBanned();
                return;
            }

            s.addBannedPlayer(p);

            getMessenger().playerBanned();
            return;
        } else if (LedgerCommandKeywords.UNBAN.contains(mainArg)) {
            if (!s.getBannedPlayers().contains(p)) {
                getMessenger().playerNotBanned();
                return;
            }

            s.removeBannedPlayer(p);

            getMessenger().playerUnBanned();
            return;
        } else if (LedgerCommandKeywords.MUTE.contains(mainArg)) {
            if (s.getMutedPlayers().contains(p)) {
                getMessenger().playerAlreadyMuted();
                return;
            }

            s.addMutedPlayer(p);

            getMessenger().playerMuted();
            return;
        } else if (LedgerCommandKeywords.UN_MUTE.contains(mainArg)) {
            if (!s.getMutedPlayers().contains(p)) {
                getMessenger().playerNotMuted();
                return;
            }

            s.removeMutedPlayer(p);
            getMessenger().playerUnMuted();
            return;
        } else if (LedgerCommandKeywords.KICK.contains(mainArg)) {
            Player player = p.toPlayer();
            if (player == null) {
                getMessenger().playerNotOnline();
                return;
            }

            player.kickPlayer(getActor().getName() + "의 사법부에 의해 킥 처리되었습니다.");

            getMessenger().playerKicked();
            return;
        }

        getMessenger().invalidKeyword();
        return;
    }
}
