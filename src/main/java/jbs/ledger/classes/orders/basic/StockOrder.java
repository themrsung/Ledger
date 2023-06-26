package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.basic.CommodityTransferredEvent;
import jbs.ledger.events.transfers.basic.StockTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.unique.StockForwardData;
import jbs.ledger.io.types.orders.basic.StockOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.UniqueNote;
import org.bukkit.Bukkit;

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

    @Override
    public void onFulfilled(Market<Stock> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        Stock asset = market.getUnitAsset();
        asset.setQuantity(quantity);

        Cash settlement = new Cash(market.getCurrency(), price * quantity);

        if (getType().isBuy()) {
            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    settlement.copy(),
                    AssetTransferCause.ORDER_FULFILLED
            ));

            Bukkit.getPluginManager().callEvent(new StockTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    asset.copy(),
                    AssetTransferCause.ORDER_FULFILLED
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new StockTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    asset.copy(),
                    AssetTransferCause.ORDER_FULFILLED
            ));

            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    settlement.copy(),
                    AssetTransferCause.ORDER_FULFILLED
            ));
        }
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
