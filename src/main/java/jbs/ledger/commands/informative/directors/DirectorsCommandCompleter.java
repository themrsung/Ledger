package jbs.ledger.commands.informative.directors;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.interfaces.organization.Directorship;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class DirectorsCommandCompleter extends LedgerCommandAutoCompleter {
    public DirectorsCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public DirectorsCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (!(getActor() instanceof Directorship)) {
            list.add("이사회가 있는 조직 명의로만 실행 가능한 명령어입니다.");
        }

        return list;
    }
}
