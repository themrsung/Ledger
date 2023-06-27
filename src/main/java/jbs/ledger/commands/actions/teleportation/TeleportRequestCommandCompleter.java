package jbs.ledger.commands.actions.teleportation;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class TeleportRequestCommandCompleter extends LedgerCommandAutoCompleter {
    public TeleportRequestCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public TeleportRequestCommandCompleter(LedgerCommandAutoCompleter original, Assetholder actor) {
        super(original);
    }
    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            for (Assetholder h : getState().getAssetholders()) {
                list.add(h.getSearchTag());
            }
        }

        return list;
    }
}
