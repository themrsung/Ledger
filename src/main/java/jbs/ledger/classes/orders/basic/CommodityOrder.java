package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.unique.CommodityForwardData;
import jbs.ledger.io.types.orders.basic.CommodityOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public final class CommodityOrder extends AbstractOrder<Commodity> {
    public CommodityOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity,
            @Nullable UniqueNote<Cash> cashCollateral,
            @Nullable UniqueNote<Commodity> assetCollateral
    ) {
        super(uniqueId, type, sender, date, price, quantity, cashCollateral, assetCollateral);
    }

    public CommodityOrder(AbstractOrder<Commodity> copy) {
        super(copy);
    }

    @Override
    public void unregisterAssetCollateral(Market<Commodity> market) {
        market.getExchange().getCommodityForwards().remove(getAssetCollateral());
    }

    // IO
    public CommodityOrderData toData() {
        CommodityOrderData data = new CommodityOrderData(super.toData());

        if (getAssetCollateral() != null) {
            data.assetCollateral = (CommodityForwardData) getAssetCollateral().toData();
        }

        return data;
    }

    public static CommodityOrder fromData(CommodityOrderData data, LedgerState state) {
        return new CommodityOrder(
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
