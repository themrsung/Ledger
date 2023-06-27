package jbs.ledger.commands.informative.networthleaderboard;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public final class NetWorthLeaderboardCommand extends LedgerPlayerCommand {
    public NetWorthLeaderboardCommand(Ledger ledger) {
        super(ledger);
    }
    public NetWorthLeaderboardCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        ArrayList<Assetholder> assetholders = getState().getAssetholders();
        String denotation;

        if (mainArg != null && getState().isCurrency(mainArg)) {
            denotation = mainArg.toUpperCase();
        } else {
            denotation = getState().getConfig().defaultCurrency;
        }

        // Sort
        assetholders.sort((a1, a2) -> Double.compare(a2.getNetWorth(getState(), denotation), a1.getNetWorth(getState(), denotation)));
        int rankIndex = assetholders.indexOf(getActor());

        getMessenger().netWorthLeaderboard(assetholders, denotation, 15, getState());
        getMessenger().netWorthMyRank(rankIndex);
    }
}
