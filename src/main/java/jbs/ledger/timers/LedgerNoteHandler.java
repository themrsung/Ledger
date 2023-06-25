package jbs.ledger.timers;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.Option;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.assets.synthetic.UniqueNote;

import java.util.Date;

public final class LedgerNoteHandler implements Runnable {
    public LedgerNoteHandler(Ledger ledger) {
        this.state = ledger.getState();
    }
    private final LedgerState state;

    public void run() {
        Date now = new Date();

        for (Assetholder holder : state.getAssetholders()) {
            for (StackableNote<Cash> bond : holder.getBonds().get()) {
                Date expiry = bond.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    bond.onExpired(holder);
                    holder.getBonds().remove(bond);
                }
            }

            for (UniqueNote<Cash> note : holder.getNotes().get()) {
                Date expiry = note.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    note.onExpired(holder);
                    holder.getNotes().remove(note);
                }
            }

            for (UniqueNote<Commodity> commodityForward : holder.getCommodityForwards().get()) {
                Date expiry = commodityForward.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    commodityForward.onExpired(holder);
                    holder.getCommodityForwards().remove(commodityForward);
                }
            }

            for (UniqueNote<Stock> stockForward : holder.getStockForwards().get()) {
                Date expiry = stockForward.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    stockForward.onExpired(holder);
                    holder.getStockForwards().remove(stockForward);
                }
            }

            for (UniqueNote<StackableNote<Cash>> bondForward : holder.getBondForwards().get()) {
                Date expiry = bondForward.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    bondForward.onExpired(holder);
                    holder.getBondForwards().remove(bondForward);
                }
            }

            for (StackableNote<Commodity> commodityFutures : holder.getCommodityFutures().get()) {
                Date expiry = commodityFutures.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    commodityFutures.onExpired(holder);
                    holder.getCommodityFutures().remove(commodityFutures);
                }
            }

            for (StackableNote<Stock> stockFutures : holder.getStockFutures().get()) {
                Date expiry = stockFutures.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    stockFutures.onExpired(holder);
                    holder.getStockFutures().remove(stockFutures);
                }
            }

            for (Option<Cash> cashOption : holder.getCashOptions().get()) {
                Date expiry = cashOption.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    cashOption.onExpired(holder);
                    holder.getCashOptions().remove(cashOption);
                }
            }

            for (Option<Stock> stockOption : holder.getStockOptions().get()) {
                Date expiry = stockOption.getExpiration();

                if (expiry != null && expiry.before(now)) {
                    stockOption.onExpired(holder);
                    holder.getStockOptions().remove(stockOption);
                }
            }
        }
    }
}
