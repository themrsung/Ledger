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
import jbs.ledger.commands.actions.vote.VoteCommandCompleter;
import jbs.ledger.commands.administrative.manage.ManageCommandCompleter;
import jbs.ledger.commands.economy.assets.AssetsCommandCompleter;
import jbs.ledger.commands.economy.balance.BalanceCommandCompleter;
import jbs.ledger.commands.economy.bank.BankCommandCompleter;
import jbs.ledger.commands.economy.bonds.BondsCommandCompleter;
import jbs.ledger.commands.economy.cards.CardsCommandCompleter;
import jbs.ledger.commands.economy.credit.CreditRatingCommandCompleter;
import jbs.ledger.commands.economy.forwards.ForwardsCommandCompleter;
import jbs.ledger.commands.economy.futures.FuturesCommandCompleter;
import jbs.ledger.commands.economy.liabilities.LiabilitiesCommandCompleter;
import jbs.ledger.commands.economy.networth.NetWorthCommandCompleter;
import jbs.ledger.commands.economy.options.OptionsCommandCompleter;
import jbs.ledger.commands.economy.pay.PayCommandCompleter;
import jbs.ledger.commands.economy.stocks.StocksCommandCompleter;
import jbs.ledger.commands.economy.trading.BuyOrSellCommandCompleter;
import jbs.ledger.commands.economy.trading.PriceCommandCompleter;
import jbs.ledger.commands.informative.directors.DirectorsCommandCompleter;
import jbs.ledger.commands.informative.list.ListCommandCompleter;
import jbs.ledger.commands.informative.members.MembersCommandCompleter;
import jbs.ledger.commands.informative.networthleaderboard.NetWorthLeaderboardCommandCompleter;
import jbs.ledger.commands.informative.premium.PremiumCommandCompleter;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * DO NOT LOOK AT THIS CODE
 * You WILL have an aneurysm.
 */
public final class SudoCommandCompleter extends LedgerCommandAutoCompleter {
    public SudoCommandCompleter(Ledger ledger) {
        super(ledger);
    }
    public SudoCommandCompleter(LedgerCommandAutoCompleter original) {
        super(original);
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
        } else {
            Assetholder actor = getState().getAssetholder(args[0], true, true);
            if (actor != null) {
                this.setActor(actor);
            }

            String action = args[1].toLowerCase();

            // switch not used due to namespacing issues (I don't want to be typing in long semantic names for one-time use variables)
            if (LedgerCommandKeywords.CREATE.contains(action)) {
                CreateCommandCompleter ccc = new CreateCommandCompleter(this);
                return ccc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.SUDO.contains(action)) {
                // Yes, this is a thing
                // A sudoing B sudoing C sudoing D is possible (as long as the permission chain is intact)
                SudoCommandCompleter scc = new SudoCommandCompleter(this);
                return scc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.PAY.contains(action)) {
                PayCommandCompleter pcc = new PayCommandCompleter(this);
                return pcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.BUY.contains(action) || LedgerCommandKeywords.SELL.contains(action)) {
                BuyOrSellCommandCompleter bos = new BuyOrSellCommandCompleter(this);
                return bos.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.ACCEPT.contains(action) || LedgerCommandKeywords.DENY.contains(action) || LedgerCommandKeywords.CANCEL.contains(action)) {
                HandleOffersCommandCompleter hocc = new HandleOffersCommandCompleter(this);
                return hocc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.INVITE.contains(action)) {
                InviteCommandCompleter icc = new InviteCommandCompleter(this);
                return icc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.BALANCE.contains(action)) {
                BalanceCommandCompleter bcc = new BalanceCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.CREDIT_RATING.contains(action)) {
                CreditRatingCommandCompleter crcc = new CreditRatingCommandCompleter(this);
                return crcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.KICK.contains(action)) {
                KickCommandCompleter kcc = new KickCommandCompleter(this);
                return kcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.PRICE.contains(action)) {
                PriceCommandCompleter pcc = new PriceCommandCompleter(this);
                return pcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.PARDON.contains(action)) {
                PardonCommandCompleter pcc = new PardonCommandCompleter(this);
                return pcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.PUNISH.contains(action)) {
                PunishCommandCompleter pcc = new PunishCommandCompleter(this);
                return pcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.VOTE.contains(action)) {
                VoteCommandCompleter vcc = new VoteCommandCompleter(this);
                return vcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.PREMIUM.contains(action)) {
                PremiumCommandCompleter pcc = new PremiumCommandCompleter(this);
                return pcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.DIRECTORS.contains(action)) {
                DirectorsCommandCompleter dcc = new DirectorsCommandCompleter(this);
                return dcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.MEMBERS.contains(action)) {
                MembersCommandCompleter mcc = new MembersCommandCompleter(this);
                return mcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.LIST.contains(action)) {
                ListCommandCompleter lcc = new ListCommandCompleter(this);
                return lcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.NET_WORTH_LEADERBOARD.contains(action)) {
                NetWorthLeaderboardCommandCompleter nwlcc = new NetWorthLeaderboardCommandCompleter(this);
                return nwlcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.MANAGE.contains(action)) {
                ManageCommandCompleter mcc = new ManageCommandCompleter(this);
                return mcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.ASSETS.contains(action)) {
                AssetsCommandCompleter acc = new AssetsCommandCompleter(this);
                return acc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.LIABILITIES.contains(action)) {
                LiabilitiesCommandCompleter lcc = new LiabilitiesCommandCompleter(this);
                return lcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.NET_WORTH.contains(action)) {
                NetWorthCommandCompleter nwcc = new NetWorthCommandCompleter(this);
                return nwcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.BANK.contains(action)) {
                BankCommandCompleter bcc = new BankCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.BONDS.contains(action)) {
                BondsCommandCompleter bcc = new BondsCommandCompleter(this);
                return bcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.CARDS.contains(action)) {
                CardsCommandCompleter ccc = new CardsCommandCompleter(this);
                return ccc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.FORWARDS.contains(action)) {
                ForwardsCommandCompleter fcc = new ForwardsCommandCompleter(this);
                return fcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.FUTURES.contains(action)) {
                FuturesCommandCompleter fcc = new FuturesCommandCompleter(this);
                return fcc.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.OPTIONS.contains(action)) {
                OptionsCommandCompleter occ = new OptionsCommandCompleter(this);
                return occ.onSudoComplete(args, getActor());
            } else if (LedgerCommandKeywords.STOCKS.contains(action)) {
                StocksCommandCompleter scc = new StocksCommandCompleter(this);
                return scc.onSudoComplete(args, getActor());
            }
        }

        return results;
    }
}
