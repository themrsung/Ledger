package jbs.ledger.commands.administrative;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.commands.actions.*;
import jbs.ledger.commands.economy.*;
import jbs.ledger.commands.informative.ListCommand;
import jbs.ledger.interfaces.common.Economic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public final class SudoCommand extends LedgerPlayerCommand {
    public SudoCommand(Ledger ledger) {
        super(ledger);
    }
    public SudoCommand(LedgerPlayerCommand originalCommand, Economic actor) {
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

        String main = argsAfterMain[0].toLowerCase();
        String[] argsAfter = argsAfterMain.length > 1 ? Arrays.copyOfRange(argsAfterMain, 1, argsAfterMain.length) : new String[] {};

        if (LedgerCommandKeywords.PAY.contains(mainArg)) {
            PayCommand pay = new PayCommand(this, actor);
            pay.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.CREATE.contains(mainArg)) {
            CreateCommand create = new CreateCommand(this, actor);
            create.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.BALANCE.contains(mainArg)) {
            BalanceCommand balance = new BalanceCommand(this, actor);
            balance.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.STOCKS.contains(mainArg)) {
            StocksCommand stocks = new StocksCommand(this, actor);
            stocks.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.BONDS.contains(mainArg)) {
            BondsCommand bonds = new BondsCommand(this, actor);
            bonds.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.FUTURES.contains(mainArg)) {
            FuturesCommand futures = new FuturesCommand(this, actor);
            futures.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.FORWARDS.contains(mainArg)) {
            ForwardsCommand forwards = new ForwardsCommand(this, actor);
            forwards.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.INVITE.contains(mainArg)) {
            InviteCommand invite = new InviteCommand(this, actor);
            invite.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.KICK.contains(mainArg)) {
            KickCommand kick = new KickCommand(this, actor);
            kick.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.PUNISH.contains(mainArg)) {
            PunishCommand punish = new PunishCommand(this, actor);
            punish.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.YES.contains(mainArg)) {
            YesCommand yes = new YesCommand(this, actor);
            yes.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.NO.contains(mainArg)) {
            NoCommand no = new NoCommand(this, actor);
            no.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.LIST.contains(mainArg)) {
            ListCommand list = new ListCommand(this, actor);
            list.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.MEMBERS.contains(mainArg)) {
            MembersCommand members = new MembersCommand(this, actor);
            members.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.DIRECTORS.contains(mainArg)) {
            DirectorsCommand directors = new DirectorsCommand(this, actor);
            directors.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.NET_WORTH.contains(mainArg)) {
            NetWorthCommand netWorth = new NetWorthCommand(this, actor);
            netWorth.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.ASSETS.contains(mainArg)) {
            AssetsCommand assets = new AssetsCommand(this, actor);
            assets.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.LIABILITIES.contains(mainArg)) {
            LiabilitiesCommand liabilities = new LiabilitiesCommand(this, actor);
            liabilities.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.CARDS.contains(mainArg)) {
            CardsCommand cards = new CardsCommand(this, actor);
            cards.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.VOTE.contains(mainArg)) {
            VoteCommand vote = new VoteCommand(this, actor);
            vote.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.HOME.contains(mainArg)) {
            HomeCommand home = new HomeCommand(this, actor);
            home.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.SET_HOME.contains(mainArg)) {
            SetHomeCommand setHome = new SetHomeCommand(this, actor);
            setHome.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.DELETE_HOME.contains(mainArg)) {
            DeleteHomeCommand deleteHome = new DeleteHomeCommand(this, actor);
            deleteHome.onSudoCommand(main, argsAfter);
            return;
        }

        if (LedgerCommandKeywords.MANAGE.contains(mainArg)) {
            ManageCommand manage = new ManageCommand(this, actor);
            manage.onSudoCommand(main, argsAfter);
        }

        if (LedgerCommandKeywords.BANK.contains(mainArg)) {
            BankCommand bank = new BankCommand(this, actor);
            bank.onSudoCommand(main, argsAfter);
        }

        getMessenger().invalidKeyword();
    }
}
