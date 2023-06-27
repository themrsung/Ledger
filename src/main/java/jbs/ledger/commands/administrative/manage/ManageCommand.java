package jbs.ledger.commands.administrative.manage;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.assetholders.corporations.finance.ForeignExchange;
import jbs.ledger.assetholders.corporations.finance.FuturesExchange;
import jbs.ledger.assetholders.corporations.finance.SecuritiesExchange;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ManageCommand extends LedgerPlayerCommand {
    public ManageCommand(Ledger ledger) {
        super(ledger);
    }
    public ManageCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().insufficientArgs();
            return;
        }

        String action = mainArg.toLowerCase();

        if (getActor() instanceof Bank) {
            if (LedgerCommandKeywords.SET_INTEREST_RATE.contains(action)) {
                if (argsAfterMain.length < 1) {
                    getMessenger().insufficientArgs();
                    return;
                }

                try {
                    float rate = Float.parseFloat(argsAfterMain[0]) / 100;

                    Bank bank = (Bank) getActor();

                    bank.setInterestRate(rate);
                    getMessenger().assetholderPropertySet();
                    return;
                } catch (NumberFormatException e) {
                    getMessenger().invalidNumber();
                    return;
                }
            }
        } else if (getActor() instanceof ForeignExchange) {
            // FIXME TODO FIXME TODO FIXME TODO
            if (LedgerCommandKeywords.LIST_ASSET.contains(action)) {

            } else if (LedgerCommandKeywords.DELIST_ASSET.contains(action)) {

            } else if (LedgerCommandKeywords.LISTED_ASSETS.contains(action)) {

            } else if (LedgerCommandKeywords.SET_BUYER_FEE_RATE.contains(action)) {

            } else if (LedgerCommandKeywords.SET_SELLER_FEE_RATE.contains(action)) {

            }
        } else if (getActor() instanceof FuturesExchange) {
            if (LedgerCommandKeywords.LIST_ASSET.contains(action)) {

            } else if (LedgerCommandKeywords.DELIST_ASSET.contains(action)) {

            } else if (LedgerCommandKeywords.LISTED_ASSETS.contains(action)) {

            } else if (LedgerCommandKeywords.SET_BUYER_FEE_RATE.contains(action)) {

            } else if (LedgerCommandKeywords.SET_SELLER_FEE_RATE.contains(action)) {

            }

        } else if (getActor() instanceof SecuritiesExchange) {
            if (LedgerCommandKeywords.LIST_ASSET.contains(action)) {

            } else if (LedgerCommandKeywords.DELIST_ASSET.contains(action)) {

            } else if (LedgerCommandKeywords.LISTED_ASSETS.contains(action)) {

            } else if (LedgerCommandKeywords.SET_BUYER_FEE_RATE.contains(action)) {

            } else if (LedgerCommandKeywords.SET_SELLER_FEE_RATE.contains(action)) {

            }
        }
    }
}
