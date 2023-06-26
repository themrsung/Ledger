package jbs.ledger.classes.orders.synthetic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.futures.CommodityFuturesTransferredEvent;
import jbs.ledger.events.transfers.futures.StockFuturesTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.orders.synthetic.StockFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public final class StockFuturesOrder extends AbstractOrder<StackableNote<Stock>> {
    public StockFuturesOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public StockFuturesOrder(AbstractOrder<StackableNote<Stock>> copy) {
        super(copy);
    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<StackableNote<Stock>> market) {

    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<StackableNote<Stock>> market) {

    }

    @Override
    public void onFulfilled(Market<StackableNote<Stock>> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        // LISTED FUTURES CONTRACTS MUST HAVE THEIR EXCHANGE AS THE DELIVERER
        // Short positions have a negative quantity, and will still work

        StackableNote<Stock> asset = market.getUnitAsset();
        asset.setQuantity(quantity);

        Cash settlement = new Cash(market.getCurrency(), price * quantity);

        if (getType().isBuy()) {
            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    settlement,
                    AssetTransferCause.ORDER_FULFILLED
            ));

            Bukkit.getPluginManager().callEvent(new StockFuturesTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    asset,
                    AssetTransferCause.ORDER_FULFILLED
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new StockFuturesTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    asset,
                    AssetTransferCause.ORDER_FULFILLED
            ));

            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    settlement,
                    AssetTransferCause.ORDER_FULFILLED
            ));
        }
    }

    // IO
    public StockFuturesOrderData toData() {
        return new StockFuturesOrderData(super.toData());
    }

    public static StockFuturesOrder fromData(StockFuturesOrderData data, LedgerState state) {
        return new StockFuturesOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}
