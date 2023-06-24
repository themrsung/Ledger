package jbs.ledger.classes.orders.synthetic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.futures.CommodityFuturesTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.orders.synthetic.CommodityFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.StackableNote;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public final class CommodityFuturesOrder extends AbstractOrder<StackableNote<Commodity>> {
    public CommodityFuturesOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public CommodityFuturesOrder(AbstractOrder<StackableNote<Commodity>> copy) {
        super(copy);
    }

    @Override
    public void onFulfilled(Market<StackableNote<Commodity>> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        // LISTED FUTURES CONTRACTS MUST HAVE THEIR EXCHANGE AS THE DELIVERER
        // Short positions have a negative quantity, and will still work

        StackableNote<Commodity> asset = market.getUnitAsset();
        asset.setQuantity(quantity);

        Cash settlement = new Cash(market.getCurrency(), price * quantity);

        if (getType().isBuy()) {
            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    settlement,
                    "Buy order fulfilled"
            ));

            Bukkit.getPluginManager().callEvent(new CommodityFuturesTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    asset,
                    "Buy order fulfilles"
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new CommodityFuturesTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    asset,
                    "Sell order fulfilles"
            ));

            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    settlement,
                    "Sell order fulfilled" // NOW DO THIS TO ALL ORDER TYPES
            ));
        }
    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<StackableNote<Commodity>> market) {

    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<StackableNote<Commodity>> market) {

    }

    // IO
    public CommodityFuturesOrderData toData() {
        return new CommodityFuturesOrderData(super.toData());
    }

    public static CommodityFuturesOrder fromData(CommodityFuturesOrderData data, LedgerState state) {
        return new CommodityFuturesOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}
