package jbs.ledger.commands.actions.message;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class MessageCommandCompleter extends LedgerCommandAutoCompleter {
    public MessageCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> resutls = new ArrayList<>();

        if (args.length < 2) {
            for (Assetholder h : getState().getAssetholders()) {
                resutls.add(h.getSearchTag());
            }
        } else {
            resutls.add("메시지를 입력하세요.");
        }

        return resutls;
    }
}
