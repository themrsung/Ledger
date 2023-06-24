package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.io.types.navigation.Address;
import jbs.ledger.utils.TypeUtils;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SpawnCommand extends LedgerPlayerCommand {
    public SpawnCommand(Ledger ledger) {
        super(ledger);
    }
    public SpawnCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        Address spawnAddress = getConfig().serverSpawn;
        if (spawnAddress == null) {
            return;
        }

        Location spawn = TypeUtils.addressToLocation(getConfig().serverSpawn);
        if (spawn == null) {
            getMessenger().invalidTeleportDestination();
            return;
        }


        getPlayer().teleport(spawn);
        getMessenger().teleportSucceeded();
    }
}
