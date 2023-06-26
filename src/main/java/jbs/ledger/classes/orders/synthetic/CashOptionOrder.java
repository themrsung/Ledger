package jbs.ledger.classes.orders.synthetic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.futures.CommodityFuturesTransferredEvent;
import jbs.ledger.events.transfers.options.CashOptionTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.orders.synthetic.CashOptionOrderData;
import jbs.ledger.io.types.orders.synthetic.CommodityFuturesOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.synthetic.Option;
import jbs.ledger.types.assets.synthetic.StackableNote;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public final class CashOptionOrder extends AbstractOrder<Option<Cash>> {
    public CashOptionOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public CashOptionOrder(AbstractOrder<Option<Cash>> copy) {
        super(copy);
    }

    @Override
    public void onFulfilled(Market<Option<Cash>> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        // LISTED OPTION CONTRACTS MUST HAVE THEIR EXCHANGE AS THE DELIVERER
        // Short positions have a negative quantity, and will still work

        Option<Cash> asset = market.getUnitAsset();
        asset.setQuantity(quantity);

        Cash settlement = new Cash(market.getCurrency(), price * quantity);

        if (getType().isBuy()) {
            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    settlement,
                    AssetTransferCause.ORDER_FULFILLED
            ));

            Bukkit.getPluginManager().callEvent(new CashOptionTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    asset,
                    AssetTransferCause.ORDER_FULFILLED
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new CashOptionTransferredEvent(
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

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<Option<Cash>> market) {

    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<Option<Cash>> market) {

    }

    // IO
    public CashOptionOrderData toData() {
        return new CashOptionOrderData(super.toData());
    }

    public static CashOptionOrder fromData(CashOptionOrderData data, LedgerState state) {
        return new CashOptionOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}
