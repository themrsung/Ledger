package jbs.ledger.commands.actions.gps;

import jbs.ledger.Ledger;
import jbs.ledger.classes.navigation.GpsEntry;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class GpsCommandCompleter extends LedgerCommandAutoCompleter {
    public GpsCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            list.addAll(LedgerCommandKeywords.LIST);
            list.addAll(LedgerCommandKeywords.ADD);
            list.addAll(LedgerCommandKeywords.DELETE);
        } else if (args.length < 3) {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.DELETE.contains(action)) {
                for (GpsEntry ge : getPerson().getGpsEntries()) {
                    list.add(ge.getName());
                }
            } else if (LedgerCommandKeywords.ADD.contains(action)) {
                list.add("현재 위치를 GPS에 저장합니다. 저장할 이름을 입력해주세요.");
            }
        }

        return list;
    }
}
