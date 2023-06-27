package jbs.ledger.commands.informative.networthleaderboard;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class NetWorthLeaderboardCommandCompleter extends LedgerCommandAutoCompleter {
    public NetWorthLeaderboardCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public NetWorthLeaderboardCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(getState().getCurrencies());
        }

        return list;
    }
}
