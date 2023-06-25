package jbs.ledger.commands.administrative.sudo;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.commands.actions.*;
import jbs.ledger.commands.actions.create.CreateCommand;
import jbs.ledger.commands.actions.home.DeleteHomeCommand;
import jbs.ledger.commands.actions.home.HomeCommand;
import jbs.ledger.commands.actions.home.SetHomeCommand;
import jbs.ledger.commands.actions.offers.AcceptCommand;
import jbs.ledger.commands.actions.offers.CancelCommand;
import jbs.ledger.commands.actions.offers.DenyCommand;
import jbs.ledger.commands.administrative.DirectorsCommand;
import jbs.ledger.commands.administrative.ManageCommand;
import jbs.ledger.commands.administrative.MembersCommand;
import jbs.ledger.commands.economy.*;
import jbs.ledger.commands.economy.balance.BalanceCommand;
import jbs.ledger.commands.economy.credit.CreditRatingCommand;
import jbs.ledger.commands.economy.pay.PayCommand;
import jbs.ledger.commands.informative.ListCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public final class SudoCommand extends LedgerPlayerCommand {
    public SudoCommand(Ledger ledger) {
        super(ledger);
    }

    public SudoCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 1) {
            getMessenger().insufficientArgs();
            return;
        }

        Assetholder actor = getState().getAssetholder(mainArg, true, true);
        if (actor == null) {
            getMessenger().assetholderNotFound();
            return;
        }

        String sudoAction = argsAfterMain[0].toLowerCase();
        String main = argsAfterMain.length >= 2 ? argsAfterMain[1].toLowerCase() : null;
        String[] argsAfter = argsAfterMain.length > 2 ? Arrays.copyOfRange(argsAfterMain, 2, argsAfterMain.length) : new String[]{};

        if (LedgerCommandKeywords.PAY.contains(sudoAction)) {
            PayCommand pay = new PayCommand(this, actor);
            pay.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.CREATE.contains(sudoAction)) {
            CreateCommand create = new CreateCommand(this, actor);
            create.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.BALANCE.contains(sudoAction)) {
            BalanceCommand balance = new BalanceCommand(this, actor);
            balance.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.STOCKS.contains(sudoAction)) {
            StocksCommand stocks = new StocksCommand(this, actor);
            stocks.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.BONDS.contains(sudoAction)) {
            BondsCommand bonds = new BondsCommand(this, actor);
            bonds.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.FUTURES.contains(sudoAction)) {
            FuturesCommand futures = new FuturesCommand(this, actor);
            futures.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.FORWARDS.contains(sudoAction)) {
            ForwardsCommand forwards = new ForwardsCommand(this, actor);
            forwards.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.INVITE.contains(sudoAction)) {
            InviteCommand invite = new InviteCommand(this, actor);
            invite.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.KICK.contains(sudoAction)) {
            KickCommand kick = new KickCommand(this, actor);
            kick.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.PUNISH.contains(sudoAction)) {
            PunishCommand punish = new PunishCommand(this, actor);
            punish.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.ACCEPT.contains(sudoAction)) {
            AcceptCommand yes = new AcceptCommand(this, actor);
            yes.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.DENY.contains(sudoAction)) {
            DenyCommand no = new DenyCommand(this, actor);
            no.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.LIST.contains(sudoAction)) {
            ListCommand list = new ListCommand(this, actor);
            list.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.MEMBERS.contains(sudoAction)) {
            MembersCommand members = new MembersCommand(this, actor);
            members.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.DIRECTORS.contains(sudoAction)) {
            DirectorsCommand directors = new DirectorsCommand(this, actor);
            directors.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.NET_WORTH.contains(sudoAction)) {
            NetWorthCommand netWorth = new NetWorthCommand(this, actor);
            netWorth.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.ASSETS.contains(sudoAction)) {
            AssetsCommand assets = new AssetsCommand(this, actor);
            assets.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.LIABILITIES.contains(sudoAction)) {
            LiabilitiesCommand liabilities = new LiabilitiesCommand(this, actor);
            liabilities.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.CARDS.contains(sudoAction)) {
            CardsCommand cards = new CardsCommand(this, actor);
            cards.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.VOTE.contains(sudoAction)) {
            VoteCommand vote = new VoteCommand(this, actor);
            vote.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.HOME.contains(sudoAction)) {
            HomeCommand home = new HomeCommand(this, actor);
            home.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.SET_HOME.contains(sudoAction)) {
            SetHomeCommand setHome = new SetHomeCommand(this, actor);
            setHome.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.DELETE_HOME.contains(sudoAction)) {
            DeleteHomeCommand deleteHome = new DeleteHomeCommand(this, actor);
            deleteHome.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.MANAGE.contains(sudoAction)) {
            ManageCommand manage = new ManageCommand(this, actor);
            manage.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.BANK.contains(sudoAction)) {
            BankCommand bank = new BankCommand(this, actor);
            bank.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.CANCEL.contains(sudoAction)) {
            CancelCommand cancel = new CancelCommand(this, actor);
            cancel.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.SUDO.contains(sudoAction)) {
            SudoCommand sudo = new SudoCommand(this, actor);
            sudo.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.OPTIONS.contains(sudoAction)) {
            OptionsCommand options = new OptionsCommand(this, actor);
            options.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.CREDIT_RATING.contains(sudoAction)) {
            CreditRatingCommand creditRating = new CreditRatingCommand(this, actor);
            creditRating.onSudoCommand(main, argsAfter);
            return;
        }

        getMessenger().invalidKeyword();
    }
}
