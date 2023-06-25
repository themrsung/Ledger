package jbs.ledger.classes.orders.basic;

import jbs.ledger.classes.orders.AbstractOrder;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.events.transfers.basic.BondTransferredEvent;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.futures.StockFuturesTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.stackable.BondData;
import jbs.ledger.io.types.orders.basic.BondOrderData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public final class BondOrder extends AbstractOrder<StackableNote<Cash>> {
    public BondOrder(
            UUID uniqueId,
            OrderType type,
            Economic sender,
            Date date,
            double price,
            long quantity
    ) {
        super(uniqueId, type, sender, date, price, quantity, null, null);
    }

    public BondOrder(AbstractOrder<StackableNote<Cash>> copy) {
        super(copy);
    }

    @Override
    public void onFulfilled(Market<StackableNote<Cash>> market, double price, long quantity) {
        super.onFulfilled(market, price, quantity);

        StackableNote<Cash> asset = market.getUnitAsset();
        asset.setQuantity(quantity);

        Cash settlement = new Cash(market.getCurrency(), price * quantity);

        if (getType().isBuy()) {
            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    settlement,
                    "Buy order fulfilled"
            ));

            Bukkit.getPluginManager().callEvent(new BondTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    asset,
                    "Buy order fulfilled"
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new BondTransferredEvent(
                    getSender(),
                    market.getExchange(),
                    asset,
                    "Sell order fulfilled"
            ));

            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    market.getExchange(),
                    getSender(),
                    settlement,
                    "Sell order fulfilled"
            ));
        }
    }

    /**
     * Does nothing; Bond orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterCashCollateral(Market<StackableNote<Cash>> market) {

    }

    /**
     * Does nothing; Bond orders have no collateral.
     * @param market Does nothing.
     */
    @Override
    public void unregisterAssetCollateral(Market<StackableNote<Cash>> market) {

    }

    // IO
    public BondOrderData toData() {
        return new BondOrderData(super.toData());
    }

    public static BondOrder fromData(BondOrderData data, LedgerState state) {
        return new BondOrder(
                data.uniqueId,
                data.type,
                state.getAssetholder(data.sender),
                data.date,
                data.price,
                data.quantity
        );
    }
}
