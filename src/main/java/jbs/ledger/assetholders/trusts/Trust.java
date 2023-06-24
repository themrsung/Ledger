package jbs.ledger.assetholders.trusts;

import jbs.ledger.assetholders.Assetholder;

import java.util.UUID;

public class Trust extends Assetholder {
    public Trust(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Trust(Assetholder copy) {
        super(copy);
    }
}
