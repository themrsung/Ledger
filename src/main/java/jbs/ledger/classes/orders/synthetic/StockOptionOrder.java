package jbs.ledger.classes.orders.synthetic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.options.StockOptionTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.orders.synthetic.StockOptionOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public final class StockOptionOrder extends AbstractOrder<Option<Stock>> {
    public StockOptionOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public StockOptionOrder(AbstractOrder<Option<Stock>> copy) {
        super(copy);
    }

    @Override
    public void onFulfilled(Market<Option<Stock>> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        // LISTED OPTION CONTRACTS MUST HAVE THEIR EXCHANGE AS THE DELIVERER
        // Short positions have a negative quantity, and will still work

        Option<Stock> asset = market.getUnitAsset();
        asset.setQuantity(quantity);

        Cash settlement = new Cash(market.getCurrency(), price * quantity);

        if (getType().isBuy()) {
            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    settlement,
                    "Buy order fulfilled"
            ));

            Bukkit.getPluginManager().callEvent(new StockOptionTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    asset,
                    "Buy order fulfilles"
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new StockOptionTransferredEvent(
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
    public void unregisterCashCollateral(Market<Option<Stock>> market) {

    }

    /**
     * Does nothing; Futures orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<Option<Stock>> market) {

    }

    // IO
    public StockOptionOrderData toData() {
        return new StockOptionOrderData(super.toData());
    }

    public static StockOptionOrder fromData(StockOptionOrderData data, LedgerState state) {
        return new StockOptionOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}
