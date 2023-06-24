package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.unique.NoteData;
import jbs.ledger.io.types.orders.basic.ForexOrderData;
import jbs.ledger.state.LedgerState;
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

    // IO
    public ForexOrderData toData() {
        ForexOrderData data = new ForexOrderData(super.toData());

        if (getAssetCollateral() != null) {
            data.assetCollateral = (NoteData) getAssetCollateral().toData();
        }

        return data;
    }

    public static ForexOrder fromData(ForexOrderData data, LedgerState state) {
        return new ForexOrder(
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
