package jbs.ledger.interfaces.sovereignty;

import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.interfaces.common.Searchable;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;

public interface NationMember extends Unique, Searchable {
    default ArrayList<Nation> getNationalities(LedgerState state) {
        ArrayList<Nation> nationalities = new ArrayList<>();

        for (Nation n : state.getNations()) {
            if (n.getMembers().contains(this)) {
                nationalities.add(n);
            }
        }

        return nationalities;
    }
}
