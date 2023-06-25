package jbs.ledger.commands.economy.trading;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.markets.basic.BondMarket;
import jbs.ledger.classes.markets.basic.ForexMarket;
import jbs.ledger.classes.markets.basic.StockMarket;
import jbs.ledger.classes.markets.synthetic.CashOptionMarket;
import jbs.ledger.classes.markets.synthetic.CommodityFuturesMarket;
import jbs.ledger.classes.markets.synthetic.StockFuturesMarket;
import jbs.ledger.classes.markets.synthetic.StockOptionMarket;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.classes.orders.basic.BondOrder;
import jbs.ledger.classes.orders.basic.ForexOrder;
import jbs.ledger.classes.orders.basic.StockOrder;
import jbs.ledger.classes.orders.synthetic.CashOptionOrder;
import jbs.ledger.classes.orders.synthetic.CommodityFuturesOrder;
import jbs.ledger.classes.orders.synthetic.StockFuturesOrder;
import jbs.ledger.classes.orders.synthetic.StockOptionOrder;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.assets.basic.Cash;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public final class BuyOrSellCommand extends LedgerPlayerCommand {
    public BuyOrSellCommand(Ledger ledger) {
        super(ledger);
    }
    public BuyOrSellCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    private boolean buy = false;

    public void onBuyCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        buy = true;
        onPlayerCommand(mainArg, argsAfterMain);
    }
    public void onSellCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        buy = false;
        onPlayerCommand(mainArg, argsAfterMain);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 1) {
            getMessenger().insufficientArgs();
            return;
        }

        Market<?> market = getState().getMarket(mainArg);
        if (market == null) {
            getMessenger().marketNotFound();
            return;
        }

        Asset asset = market.getUnitAsset();
        String currency = market.getCurrency();

        double price;
        long quantity;

        boolean isMarket;

        if (argsAfterMain.length > 1) {
            price = Cash.fromInput(argsAfterMain[0], getState()).getBalance();
            try {
                quantity = Long.parseLong(argsAfterMain[1]);
                isMarket = false;
            } catch (NumberFormatException e) {
                getMessenger().invalidQuantity();
                return;
            }
        } else {
            try {
                quantity = Long.parseLong(argsAfterMain[0]);
                price = 0d;
                isMarket = true;

                if (quantity < 1) {
                    getMessenger().invalidQuantity();
                    return;
                }
            } catch (NumberFormatException e) {
                getMessenger().invalidQuantity();
                return;
            }
        }

        OrderType type = buy ? (isMarket ? OrderType.BUY_MARKET : OrderType.BUY_LIMIT) : (isMarket ? OrderType.SELL_MARKET : OrderType.SELL_LIMIT);

        switch (asset.getType()) {
            case STOCK:
                StockMarket sm = (StockMarket) market;
                StockOrder smo = new StockOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity,
                        null,
                        null
                );

                sm.placeOrder(smo);
                getMessenger().orderSubmitted(type);
                return;
            case CASH:
                ForexMarket fx = (ForexMarket) market;
                ForexOrder fxo = new ForexOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity,
                        null,
                        null
                );
                fx.placeOrder(fxo);
                getMessenger().orderSubmitted(type);
                return;
            case BOND:
                BondMarket bm = (BondMarket) market;
                BondOrder bmo = new BondOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity
                );
                bm.placeOrder(bmo);
                getMessenger().orderSubmitted(type);
                return;
            case COMMODITY_FUTURES:
                CommodityFuturesMarket cfm = (CommodityFuturesMarket) market;
                CommodityFuturesOrder cfo = new CommodityFuturesOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity
                );
                cfm.placeOrder(cfo);
                getMessenger().orderSubmitted(type);
                return;
            case STOCK_FUTURES:
                StockFuturesMarket sfm = (StockFuturesMarket) market;
                StockFuturesOrder sfo = new StockFuturesOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity
                );
                sfm.placeOrder(sfo);
                getMessenger().orderSubmitted(type);
                return;
            case STOCK_OPTION:
                StockOptionMarket som = (StockOptionMarket) market;
                StockOptionOrder soo = new StockOptionOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity
                );
                som.placeOrder(soo);
                getMessenger().orderSubmitted(type);
                return;
            case CASH_OPTION:
                CashOptionMarket com = (CashOptionMarket) market;
                CashOptionOrder coo = new CashOptionOrder(
                        UUID.randomUUID(),
                        type,
                        getActor(),
                        new Date(),
                        price,
                        quantity
                );
                com.placeOrder(coo);
                getMessenger().orderSubmitted(type);
                return;


        }

        getMessenger().unknownError();
        return;
    }
}
