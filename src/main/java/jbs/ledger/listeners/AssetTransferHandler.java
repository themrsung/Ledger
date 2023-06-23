package jbs.ledger.listeners;

import jbs.ledger.Ledger;
import jbs.ledger.events.transfers.AssetTransferredEvent;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.types.assets.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

/**
 * A Listener that handles asset transfers
 */
public class AssetTransferHandler extends LedgerListener {
    public AssetTransferHandler(Ledger ledger) {
        super(ledger);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCashTransferred(AssetTransferredEvent<Cash> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        Cash a = e.getAsset();

        onCashTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityTransferred(AssetTransferredEvent<Commodity> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        Commodity a = e.getAsset();

        onCommodityTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockTransferred(AssetTransferredEvent<Stock> e) {
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
    public void onNoteTransferred(AssetTransferredEvent<UniqueNote<Cash>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        UniqueNote<Cash> a = e.getAsset();

        onNoteTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityForwardTransferred(AssetTransferredEvent<UniqueNote<Commodity>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        UniqueNote<Commodity> a = e.getAsset();

        onCommodityForwardTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockForwardTransferred(AssetTransferredEvent<UniqueNote<Stock>> e) {
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
    public void onBondTransferred(AssetTransferredEvent<StackableNote<Cash>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        StackableNote<Cash> a = e.getAsset();

        onBondTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityFuturesTransferred(AssetTransferredEvent<StackableNote<Commodity>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        StackableNote<Commodity> a = e.getAsset();

        onCommodityFuturesTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockFuturesTransferred(AssetTransferredEvent<StackableNote<Stock>> e) {
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

    //
    // Conditional Notes
    //

    @EventHandler(priority = EventPriority.MONITOR)
    public void onForexOptionTransferred(AssetTransferredEvent<ConditionalNote<Cash>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        ConditionalNote<Cash> a = e.getAsset();

        onForexOptionTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommodityOptionTransferred(AssetTransferredEvent<ConditionalNote<Commodity>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        ConditionalNote<Commodity> a = e.getAsset();

        onCommodityOptionTransfer(s, r, a);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockOptionTransferred(AssetTransferredEvent<ConditionalNote<Stock>> e) {
        Economic s = e.getSender();
        Economic r = e.getRecipient();
        ConditionalNote<Stock> a = e.getAsset();

        onStockOptionTransfer(s, r, a);
    }

    private static void onForexOptionTransfer(Economic s, Economic r, ConditionalNote<Cash> a) {
        s.getForexOptions().remove(a);
        r.getForexOptions().add(a);
    }

    private static void onCommodityOptionTransfer(Economic s, Economic r, ConditionalNote<Commodity> a) {
        s.getCommodityOptions().remove(a);
        r.getCommodityOptions().add(a);
    }

    private static void onStockOptionTransfer(Economic s, Economic r, ConditionalNote<Stock> a) {
        s.getStockOptions().remove(a);
        r.getStockOptions().add(a);
    }
}


