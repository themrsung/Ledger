package jbs.ledger.commands.actions.gps;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.navigation.GpsEntry;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;

public final class GpsCommand extends LedgerPlayerCommand {
    public GpsCommand(Ledger ledger) {
        super(ledger);
    }
    public GpsCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {

        if (!getPerson().isPremium()) {
            getMessenger().commandRequiresPremiumStatus();
            return;
        }

        if (mainArg != null && LedgerCommandKeywords.ADD.contains(mainArg)) {
            Location here = getPlayer().getLocation();
            String name = argsAfterMain.length >= 1 ? argsAfterMain[0] : Integer.toString(getPerson().getGpsEntries().size());

            GpsEntry entry = new GpsEntry(
                    here,
                    name
            );

            getPerson().addGpsEntry(entry);
            getMessenger().gpsEntryAdded();
        } else if (mainArg != null && LedgerCommandKeywords.DELETE.contains(mainArg)) {
            if (argsAfterMain.length < 1) {
                getMessenger().insufficientArgs();
                return;
            }

            GpsEntry entry = getPerson().getGpsEntry(argsAfterMain[0]);
            if (entry == null) {
                getMessenger().gpsEntryNotFound();
                return;
            }

            getPerson().removeGpsEntry(entry);
            getMessenger().gpsEntryRemoved();
        } else {
            getMessenger().gpsEntryListHeader();
            for (GpsEntry ge : getPerson().getGpsEntries()) {
                getMessenger().gpsEntryInformation(ge);
            }
        }
    }
}
