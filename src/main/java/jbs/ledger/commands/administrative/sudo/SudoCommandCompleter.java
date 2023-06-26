package jbs.ledger.commands.administrative.sudo;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.actions.create.CreateCommandCompleter;
import jbs.ledger.commands.actions.invite.InviteCommandCompleter;
import jbs.ledger.commands.actions.kick.KickCommandCompleter;
import jbs.ledger.commands.actions.offers.HandleOffersCommandCompleter;
import jbs.ledger.commands.actions.pardon.PardonCommandCompleter;
import jbs.ledger.commands.actions.punish.PunishCommandCompleter;
import jbs.ledger.commands.economy.balance.BalanceCommandCompleter;
import jbs.ledger.commands.economy.credit.CreditRatingCommandCompleter;
import jbs.ledger.commands.economy.pay.PayCommandCompleter;
import jbs.ledger.commands.economy.trading.BuyOrSellCommandCompleter;
import jbs.ledger.commands.economy.trading.PriceCommandCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SudoCommandCompleter extends LedgerCommandAutoCompleter {
    public SudoCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length < 2) {
            for (Assetholder h : getState().getAssetholders()) {
                results.add(h.getSearchTag());
            }
        } else if (args.length < 3) {
            results.addAll(LedgerCommandKeywords.PAY);
            results.addAll(LedgerCommandKeywords.CREATE);
            results.addAll(LedgerCommandKeywords.BALANCE);
            results.addAll(LedgerCommandKeywords.STOCKS);
            results.addAll(LedgerCommandKeywords.BONDS);
            results.addAll(LedgerCommandKeywords.FUTURES);
            results.addAll(LedgerCommandKeywords.FORWARDS);
            results.addAll(LedgerCommandKeywords.INVITE);
            results.addAll(LedgerCommandKeywords.KICK);
            results.addAll(LedgerCommandKeywords.PUNISH);
            results.addAll(LedgerCommandKeywords.ACCEPT);
            results.addAll(LedgerCommandKeywords.DENY);
            results.addAll(LedgerCommandKeywords.LIST);
            results.addAll(LedgerCommandKeywords.MEMBERS);
            results.addAll(LedgerCommandKeywords.DIRECTORS);
            results.addAll(LedgerCommandKeywords.NET_WORTH);
            results.addAll(LedgerCommandKeywords.ASSETS);
            results.addAll(LedgerCommandKeywords.LIABILITIES);
            results.addAll(LedgerCommandKeywords.VOTE);
            results.addAll(LedgerCommandKeywords.HOME);
            results.addAll(LedgerCommandKeywords.SET_HOME);
            results.addAll(LedgerCommandKeywords.DELETE_HOME);
            results.addAll(LedgerCommandKeywords.MANAGE);
            results.addAll(LedgerCommandKeywords.BANK);
            results.addAll(LedgerCommandKeywords.CANCEL);
            results.addAll(LedgerCommandKeywords.SUDO);
            results.addAll(LedgerCommandKeywords.OPTIONS);
            results.addAll(LedgerCommandKeywords.CREDIT_RATING);
            results.addAll(LedgerCommandKeywords.BUY);
            results.addAll(LedgerCommandKeywords.SELL);
            results.addAll(LedgerCommandKeywords.PRICE);
            results.addAll(LedgerCommandKeywords.PARDON);
        } else  {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.CREATE.contains(action)) {
                CreateCommandCompleter ccc = new CreateCommandCompleter(getLedger());
                return ccc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.SUDO.contains(action)){
                SudoCommandCompleter scc = new SudoCommandCompleter(getLedger());
                return scc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.PAY.contains(action)) {
                PayCommandCompleter pcc = new PayCommandCompleter(getLedger());
                return pcc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.BUY.contains(action) || LedgerCommandKeywords.SELL.contains(action)) {
                BuyOrSellCommandCompleter bos = new BuyOrSellCommandCompleter(getLedger());
                return bos.onSudoComplete(args);
            } else if (LedgerCommandKeywords.ACCEPT.contains(action) || LedgerCommandKeywords.DENY.contains(action) || LedgerCommandKeywords.CANCEL.contains(action)) {
                HandleOffersCommandCompleter hocc = new HandleOffersCommandCompleter(getLedger());
                return hocc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.INVITE.contains(action)) {
                InviteCommandCompleter icc = new InviteCommandCompleter(getLedger());
                return icc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.BALANCE.contains(action)) {
                BalanceCommandCompleter bcc = new BalanceCommandCompleter(getLedger());
                return bcc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.CREDIT_RATING.contains(action)) {
                CreditRatingCommandCompleter crcc = new CreditRatingCommandCompleter(getLedger());
                return crcc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.KICK.contains(action)) {
                KickCommandCompleter kcc = new KickCommandCompleter(getLedger());
                return kcc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.PRICE.contains(action)) {
                PriceCommandCompleter pcc = new PriceCommandCompleter(getLedger());
                return pcc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.PARDON.contains(action)) {
                PardonCommandCompleter pcc = new PardonCommandCompleter(getLedger());
                return pcc.onSudoComplete(args);
            } else if (LedgerCommandKeywords.PUNISH.contains(action)) {
                PunishCommandCompleter pcc = new PunishCommandCompleter(getLedger());
                return pcc.onSudoComplete(args);
            }
        }

        return results;
    }
}
