package jbs.ledger.listeners;

import jbs.ledger.Ledger;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.events.transfers.basic.CommodityTransferredEvent;
import jbs.ledger.events.transfers.basic.StockTransferredEvent;
import jbs.ledger.events.transfers.forwards.CommodityForwardTransferredEvent;
import jbs.ledger.events.transfers.forwards.NoteTransferredEvent;
import jbs.ledger.events.transfers.forwards.StockForwardTransferredEvent;
import jbs.ledger.events.transfers.basic.BondTransferredEvent;
import jbs.ledger.events.transfers.futures.CommodityFuturesTransferredEvent;
import jbs.ledger.events.transfers.futures.StockFuturesTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Commodity;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.assets.synthetic.StackableNote;
import jbs.ledger.types.assets.synthetic.UniqueNote;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

/**
 * A Listener that handles asset transfers
 */
public final class AssetTransferHandler extends LedgerListener {
    public AssetTransferHandler(Ledger ledger) {
        super(ledger);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCashTransferred(CashTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        Cash a = e.getAsset();

        onCashTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityTransferred(CommodityTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        Commodity a = e.getAsset();

        onCommodityTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockTransferred(StockTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        Stock a = e.getAsset();

        onStockTransfer(s, r, a);
    }

    private static void onCashTransfer(Economic s, Economic r, Cash a) {
        s.getCash().remove(a);
        r.getCash().add(a);
    }

    private static void onCommodityTransfer(Economic s, Economic r, Commodity a) {
        s.getCommodities().remove(a);
        r.getCommodities().add(a);
    }

    private static void onStockTransfer(Economic s, Economic r, Stock a) {
        s.getStocks().remove(a);
        r.getStocks().add(a);
    }

    //
    // Unique Notes
    //

    @EventHandler(priority = EventPriority.MONITOR)
    public void onNoteTransferred(NoteTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        UniqueNote<Cash> a = e.getAsset();

        onNoteTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityForwardTransferred(CommodityForwardTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        UniqueNote<Commodity> a = e.getAsset();

        onCommodityForwardTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockForwardTransferred(StockForwardTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        UniqueNote<Stock> a = e.getAsset();

        onStockForwardTransfer(s, r, a);
    }

    private static void onNoteTransfer(Economic s, Economic r, UniqueNote<Cash> a) {
        s.getNotes().remove(a);
        r.getNotes().add(a);
    }

    private static void onCommodityForwardTransfer(Economic s, Economic r, UniqueNote<Commodity> a) {
        s.getCommodityForwards().remove(a);
        r.getCommodityForwards().add(a);
    }

    private static void onStockForwardTransfer(Economic s, Economic r, UniqueNote<Stock> a) {
        s.getStockForwards().remove(a);
        r.getStockForwards().add(a);
    }

    //
    // Stackable Notes
    //

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBondTransferred(BondTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        StackableNote<Cash> a = e.getAsset();

        onBondTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityFuturesTransferred(CommodityFuturesTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        StackableNote<Commodity> a = e.getAsset();

        onCommodityFuturesTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockFuturesTransferred(StockFuturesTransferredEvent e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        StackableNote<Stock> a = e.getAsset();

        onStockFuturesTransfer(s, r, a);
    }

    private static void onBondTransfer(Economic s, Economic r, StackableNote<Cash> a) {
        s.getBonds().remove(a);
        r.getBonds().add(a);
    }

    private static void onCommodityFuturesTransfer(Economic s, Economic r, StackableNote<Commodity> a) {
        s.getCommodityFutures().remove(a);
        r.getCommodityFutures().add(a);
    }

    private static void onStockFuturesTransfer(Economic s, Economic r, StackableNote<Stock> a) {
        s.getStockFutures().remove(a);
        r.getStockFutures().add(a);
    }
}


