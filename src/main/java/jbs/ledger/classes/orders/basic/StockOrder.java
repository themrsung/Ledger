package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.orders.basic.StockOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public final class StockOrder extends AbstractOrder<Stock> {
    public StockOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity,
            @Nullable UniqueNote<Cash> cashCollateral,
            @Nullable UniqueNote<Stock> assetCollateral
    ) {
        super(uniqueId, type, sender, date, price, quantity, cashCollateral, assetCollateral);
    }

    public StockOrder(AbstractOrder<Stock> copy) {
        super(copy);
    }

    @Override
    public void unregisterAssetCollateral(Market<Stock> market) {
        market.getExchange().getStockForwards().remove(getAssetCollateral());
    }

    // IO
    public StockOrderData toData() {
        StockOrderData data = new StockOrderData(super.toData());

        if (getAssetCollateral() != null) {
            data.assetCollateral = (StockForwardData) getAssetCollateral().toData();
        }

        return data;
    }

    public static StockOrder fromData(StockOrderData data, LedgerState state) {
        return new StockOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity,
                data.cashCollateral != null ? UniqueNote.fromData(data.cashCollateral, state) : null,
                data.assetCollateral != null ?UniqueNote.fromData(data.assetCollateral, state) : null
        );
    }
}
