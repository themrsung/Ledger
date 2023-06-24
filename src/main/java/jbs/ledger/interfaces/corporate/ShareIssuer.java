package jbs.ledger.interfaces.corporate;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;

public interface ShareIssuer extends Symbolic {
    default long getShareCount(LedgerState state) {
        long shareCount = 0L;

        for (Assetholder a : state.getAssetholders()) {
            Stock stock = a.getStocks().get(getSymbol());
            if (stock != null) {
                shareCount += stock.getQuantity();
            }
        }

        return shareCount;
    }

    Cash getCapital();
}
