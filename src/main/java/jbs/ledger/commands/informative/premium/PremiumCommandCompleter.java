package jbs.ledger.commands.informative.premium;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class PremiumCommandCompleter extends LedgerCommandAutoCompleter {
    public PremiumCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public PremiumCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (getPlayer().isOp()) {
            if (args.length < 2) {
                list.addAll(LedgerCommandKeywords.ADD);
                list.addAll(LedgerCommandKeywords.DELETE);
            } else if (args.length < 3) {
                for (Person p : getState().getPeople()) {
                    list.add(p.getSearchTag());
                }
            } else if (args.length < 4) {
                String action = args[0].toLowerCase();
                if (LedgerCommandKeywords.ADD.contains(action)) {
                    list.add("추가할 일수를 입력하세요. 입력하지 않을 경우 영구 프리미엄으로 설정됩니다.");
                } else if (LedgerCommandKeywords.DELETE.contains(action)) {
                    list.add("재거할 일수를 입력하세요. 입력하지 않을 경우 프리미엄이 해제됩니다.");
                }
            }
        } else {
            if (args.length < 2) {
                list.add("내 프리미엄 상태를 조회합니다.");
            }
        }

        return list;
    }
}
