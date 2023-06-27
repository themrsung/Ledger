package jbs.ledger.commands.economy.networth;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class NetWorthCommandCompleter extends LedgerCommandAutoCompleter {
    public NetWorthCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public NetWorthCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(getState().getCurrencies());

            for (Assetholder a : getState().getAssetholders()) {
                list.add(a.getSearchTag());
            }
        } else if (args.length < 3) {
            list.addAll(getState().getCurrencies());
        }

        return list;
    }
}
