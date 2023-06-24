package jbs.ledger.assetholders.foundations;

import jbs.ledger.assetholders.Assetholder;

import java.util.UUID;

public class Foundation extends Assetholder {

    public Foundation(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Foundation(Assetholder copy) {
        super(copy);
    }
}
