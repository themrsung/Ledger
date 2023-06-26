package jbs.ledger.types.assets.synthetic;

import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.basic.CommodityTransferredEvent;
import jbs.ledger.events.transfers.basic.StockTransferredEvent;
import jbs.ledger.interfaces.assets.Asset;
import jbs.ledger.interfaces.assets.Delayed;
import jbs.ledger.interfaces.assets.IntegralAsset;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.Date;

public abstract class Note<D extends Asset> implements IntegralAsset, Delayed<D> {
    /**
     * Issues a note
     * @param symbol Unique symbol of this note
     * @param delivery Asset delivered on expiration
     * @param deliverer Deliverer of the asset
     * @param expiration Expiration date, set to null for a perpetual note
     * @param quantity How many notes are issued (NOT the quantity of underlying to be delivered)
     */
    public Note(
            String symbol,
            D delivery,
            Economic deliverer,
            @Nullable Date expiration,
            long quantity
    ) {
        this.symbol = symbol;
        this.delivery = delivery;
        this.deliverer = deliverer;
        this.expiration = expiration;
        this.quantity = quantity;
    }

    public Note(final Note<D> copy) {
        this.symbol = copy.symbol;
        this.delivery = copy.delivery;
        this.deliverer = copy.deliverer;
        this.expiration = copy.expiration;
        this.quantity = copy.quantity;
    }

    private final String symbol;
    private final D delivery;
    private final Economic deliverer;
    @Nullable
    private final Date expiration;
    private long quantity;

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public D getDelivery() {
        return delivery;
    }

    @Override
    @Nullable
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public Economic getDeliverer() {
        return deliverer;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    // Expiration

    @Override
    public void onExpired(Economic assetholder) {
        switch (getDelivery().getType()) {
            case CASH:
                Cash cash = (Cash) getDelivery();

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        cash,
                        AssetTransferCause.NOTE_EXPIRED
                ));

                break;
            case COMMODITY:
                Commodity item = (Commodity) getDelivery();

                Bukkit.getPluginManager().callEvent(new CommodityTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        item,
                        AssetTransferCause.NOTE_EXPIRED
                ));

                break;
            case STOCK:
                Stock stock = (Stock) getDelivery();

                Bukkit.getPluginManager().callEvent(new StockTransferredEvent(
                        getDeliverer(),
                        assetholder,
                        stock,
                        AssetTransferCause.NOTE_EXPIRED
                ));

                break;
        }
    }
}
