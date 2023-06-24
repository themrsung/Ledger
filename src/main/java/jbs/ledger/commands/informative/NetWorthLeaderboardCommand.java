package jbs.ledger.commands.informative;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class NetWorthLeaderboardCommand extends LedgerPlayerCommand {
    public NetWorthLeaderboardCommand(Ledger ledger) {
        super(ledger);
    }
    public NetWorthLeaderboardCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {

    }
}
