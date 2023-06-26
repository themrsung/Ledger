package jbs.ledger.commands.actions.kick;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Trusteeship;
import jbs.ledger.interfaces.sovereignty.NationMember;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class KickCommandCompleter extends LedgerCommandAutoCompleter {
    public KickCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            if (getActor() instanceof Trusteeship) {
                Trusteeship t = (Trusteeship) getActor();
                Assetholder trustee = t.getTrustee();
                Assetholder beneficiary = t.getBeneficiary();

                if (trustee != null) list.add(trustee.getSearchTag());
                if (beneficiary != null) list.add(beneficiary.getSearchTag());
            } else if (getActor() instanceof Nation) {
                Nation n = (Nation) getActor();
                for (NationMember m : n.getMembers()) {
                    list.add(m.getSearchTag());
                }
            } else if (getActor() instanceof Corporate) {
                Corporate c = (Corporate) getActor();
                for (Person m : c.getMembers()) {
                    list.add(m.getSearchTag());
                }
            }
        }

        return list;
    }
}
