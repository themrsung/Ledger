package jbs.ledger.commands.actions.home;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SetHomeCommand extends LedgerPlayerCommand {
    public SetHomeCommand(Ledger ledger) {
        super(ledger);
    }
    public SetHomeCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        Location location = getPlayer().getLocation();

        for (Assetholder h : getState().getAssetholders()) {
            Location existing = h.getAddress();
            if (existing != null && h.getProtectionRadius() > 0 && getActor().getProtectionRadius() > 0) {
                try {
                    long existingProtection = h.getProtectionRadius();
                    long newProtection = getActor().getProtectionRadius();
                    long buffer = 25;

                    long minimumDistance = existingProtection + newProtection + buffer;

                    if (existing.distance(location) < minimumDistance) {
                        getMessenger().addressTooCloseToAnotherAddress();
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    //
                }
            }
        }

        getActor().setAddress(location);
        getMessenger().addressSet();
    }
}
