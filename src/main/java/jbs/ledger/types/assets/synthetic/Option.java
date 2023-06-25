package jbs.ledger.types.assets.synthetic;

import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.basic.StockTransferredEvent;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.io.types.assets.synthetic.AbstractNoteData;
import jbs.ledger.io.types.assets.synthetic.stackable.*;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.AssetType;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Option
 * A note that is only delivered when the price of a certain asset is within the parameters.
 * Market type does not have to match the underlying. (e.g. A stock option dependent on the price of a bond is possible)
 * @param <D> Type of asset to deliver.
 */
public final class Option<D extends Asset> extends Note<D> {
    /**
     * Issues a stackable note
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     * @param quantity How many notes are issued (NOT the quantity of underlying to be delivered)
     */
    public Option(
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration,
            long quantity,
            OptionType optionType,
            double exercisePrice,
            Market<?> market
    ) {
        super(symbol, delivery, deliverer, expiration, quantity);

        this.optionType = optionType;
        this.exercisePrice = exercisePrice;
        this.market = market;
    }

    public Option(Option<D> copy) {
        super(copy);

        this.optionType = copy.optionType;
        this.exercisePrice = copy.exercisePrice;
        this.market = copy.market;
    }

    private final OptionType optionType;
    private final double exercisePrice;
    private final Market<?> market;

    public boolean canBeExercised() {
        double p = getMarket().getPrice();
        double e = getExercisePrice();
        Date d = new Date();

        return getOptionType().canBeExercised(p, e, d);
    }

    public Cash getExerciseSettlement() {
        String currency = getMarket().getCurrency();
        double cost = 0d;

        if (getDelivery() instanceof Cash) {
            cost = getExercisePrice() * ((Cash) getDelivery()).getBalance();
        } else if (getDelivery() instanceof Stock) {
            cost = getExercisePrice() * ((Stock) getDelivery()).getQuantity();
        }

        return new Cash(currency, cost);
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public double getExercisePrice() {
        return exercisePrice;
    }

    public Market<?> getMarket() {
        return market;
    }

    @Override
    public Option<D> copy() {
        return new Option<>(this);
    }

    @Override
    public Option<D> negate() {
        Option<D> copy = copy();

        copy.setQuantity(getQuantity() * -1);

        return copy;
    }

    // Exercising
    @Override
    public void onExpired(Economic assetholder) {
        if (assetholder.getCash().contains(getExerciseSettlement())) {
            onExercised(assetholder);
        }
    }

    public void onExercised(Economic assetholder) {
        if (getOptionType().isCall()) {
            if (getDelivery() instanceof Cash) {
                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        assetholder,
                        getDeliverer(),
                        getExerciseSettlement(),
                        "Cash call option exercised"
                ));

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        (Cash) getDelivery(),
                        "Cash call option exercised"
                ));
            } else if (getDelivery() instanceof Stock) {
                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        assetholder,
                        getDeliverer(),
                        getExerciseSettlement(),
                        "Stock call option exercised"
                ));

                Bukkit.getPluginManager().callEvent(new StockTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        (Stock) getDelivery(),
                        "Stock call option exercised"
                ));
            }
        } else {
            if (getDelivery() instanceof Cash) {
                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        getExerciseSettlement(),
                        "Cash put option exercised"
                ));

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        assetholder,
                        getDeliverer(),
                        (Cash) getDelivery(),
                        "Cash put option exercised"
                ));
            } else if (getDelivery() instanceof Stock) {
                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        getExerciseSettlement(),
                        "Stock put option exercised"
                ));

                Bukkit.getPluginManager().callEvent(new StockTransferredEvent(
                        assetholder,
                        getDeliverer(),
                        (Stock) getDelivery(),
                        "Stock put option exercised"
                ));
            }
        }
    }



    @Override
    public AssetType getType() {
        switch (getDelivery().getType()) {
            case CASH:
                return AssetType.CASH_OPTION;
            case STOCK:
                return AssetType.STOCK_OPTION;
        }

        return null;
    }

    // IO
    public static Option<Stock> fromData(StockOptionData data, LedgerState state) {
        return new Option<Stock>(
                data.symbol,
                Stock.fromData(data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity,
                data.optionType,
                data.exercisePrice,
                state.getMarket(data.market)
        );
    }
    public static Option<Cash> fromData(CashOptionData data, LedgerState state) {
        return new Option<Cash>(
                data.symbol,
                Cash.fromData(data.delivery),
                state.getAssetholder(data.deliverer),
                data.expiration,
                data.quantity,
                data.optionType,
                data.exercisePrice,
                state.getMarket(data.market)
        );
    }

    public AbstractNoteData toData() {
        if (getDelivery() instanceof Stock) {
            StockOptionData data = new StockOptionData();
            data.symbol = getSymbol();

            data.delivery = ((Stock) getDelivery()).toData();

            data.type = getType();
            data.deliverer = getDeliverer().getUniqueId();
            data.expiration = getExpiration();
            data.quantity = getQuantity();

            data.optionType = optionType;
            data.exercisePrice = exercisePrice;
            data.market = market.getUniqueId();

            return data;
        }

        return null;
    }
}
