package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public final class ForexOrder extends AbstractOrder<Cash> {
    public ForexOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity,
            @Nullable UniqueNote<Cash> cashCollateral,
            @Nullable UniqueNote<Cash> assetCollateral
    ) {
        super(uniqueId, type, sender, date, price, quantity, cashCollateral, assetCollateral);
    }

    public ForexOrder(AbstractOrder<Cash> copy) {
        super(copy);
    }

    @Override
    public void unregisterAssetCollateral(Market<Cash> market) {
        market.getExchange().getNotes().remove(getAssetCollateral());
    }
}
