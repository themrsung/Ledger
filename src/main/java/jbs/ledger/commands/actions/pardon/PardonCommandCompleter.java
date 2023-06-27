package jbs.ledger.commands.actions.pardon;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.corporations.special.SovereignCorporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class PardonCommandCompleter extends LedgerCommandAutoCompleter {
    public PardonCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public PardonCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }
    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            if (getActor() instanceof Nation) {
                Nation n = (Nation) getActor();

                for (Person p : n.getBannedPlayers()) {
                    list.add(p.getSearchTag());
                }
                for (Person p : n.getMutedPlayers()) {
                    list.add(p.getSearchTag());
                }

            } else if (getActor() instanceof SovereignCorporation) {
                SovereignCorporation sc = (SovereignCorporation) getActor();

                for (Person p : sc.getBannedPlayers()) {
                    list.add(p.getSearchTag());
                }
                for (Person p : sc.getMutedPlayers()) {
                    list.add(p.getSearchTag());
                }
            }
        }

        return list;
    }
}
