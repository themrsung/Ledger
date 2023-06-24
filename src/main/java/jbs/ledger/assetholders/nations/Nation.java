package jbs.ledger.assetholders.nations;

import jbs.ledger.assetholders.Assetholder;

import java.util.UUID;

public class Nation extends Assetholder {

    public Nation(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Nation(Assetholder copy) {
        super(copy);
    }
}
