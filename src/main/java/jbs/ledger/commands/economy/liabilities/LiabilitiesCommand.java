package jbs.ledger.commands.economy.liabilities;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.commands.economy.cards.CardsCommand;
import jbs.ledger.commands.economy.forwards.ForwardsCommand;
import jbs.ledger.commands.economy.futures.FuturesCommand;
import jbs.ledger.commands.economy.options.OptionsCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public final class LiabilitiesCommand extends LedgerPlayerCommand {
    public LiabilitiesCommand(Ledger ledger) {
        super(ledger);
    }
    public LiabilitiesCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        String denotation = getConfig().defaultCurrency;

        if (mainArg != null && !getState().isCurrency(mainArg)) {
            String action = mainArg.toLowerCase();

            String main = argsAfterMain.length >= 2 ? argsAfterMain[1].toLowerCase() : null;
            String[] argsAfter = argsAfterMain.length > 2 ? Arrays.copyOfRange(argsAfterMain, 2, argsAfterMain.length) : new String[]{};

            if (LedgerCommandKeywords.FORWARDS.contains(action)) {
                ForwardsCommand forwards = new ForwardsCommand(this, getActor());
                forwards.onSudoCommand(main, argsAfter);
                return;

            } else if (LedgerCommandKeywords.FUTURES.contains(action)) {
                FuturesCommand futures = new FuturesCommand(this, getActor());
                futures.onSudoCommand(main, argsAfter);
                return;

            } else if (LedgerCommandKeywords.OPTIONS.contains(action)) {
                OptionsCommand options = new OptionsCommand(this, getActor());
                options.onSudoCommand(main, argsAfter);
                return;

            } else if (LedgerCommandKeywords.CARDS.contains(action)) {
                CardsCommand cards = new CardsCommand(this, getActor());
                cards.onSudoCommand(main, argsAfter);
                return;
            }

        } else if (mainArg != null) {
            denotation = mainArg.toUpperCase();
        }

        getMessenger().liabilitiesList(getActor(), denotation, getState());

    }
}
