package jbs.ledger.commands.informative.members;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.interfaces.organization.Organization;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class MembersCommandCompleter extends LedgerCommandAutoCompleter {
    public MembersCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    public MembersCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (!(getActor() instanceof Organization<?>)) {
            list.add("회원이 있는 조직 명의로만 실행 가능한 명령어입니다.");
        }

        return list;
    }
}
