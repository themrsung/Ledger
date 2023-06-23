package jbs.ledger.io;

import jbs.ledger.io.types.accounts.AssetholderData;

import java.util.ArrayList;

/**
 * The save state os LedgerState.
 * Ledger only saves Accounts. Use your own IO system to save other data.
 */
public final class LedgerSaveState {
    public LedgerSaveState() {}

    public ArrayList<AssetholderData> assetholders;
}
