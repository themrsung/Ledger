package jbs.ledger.commands.actions.teleportation;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.io.types.navigation.Address;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SetSpawnCommand extends LedgerPlayerCommand {
    public SetSpawnCommand(Ledger ledger) {
        super(ledger);
    }
    public SetSpawnCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (!getPlayer().isOp()) {
            getMessenger().insufficientPermissions();
            return;
        }

        Location location = getPlayer().getLocation();
        getConfig().serverSpawn = new Address(location);
        getMessenger().custom("스폰 설정 완료");
    }
}
