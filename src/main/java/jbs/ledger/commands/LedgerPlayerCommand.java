package jbs.ledger.commands;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An even better command executor for player-only commands.
 */
public abstract class LedgerPlayerCommand extends LedgerCommand {
    public LedgerPlayerCommand(Ledger ledger) {
        super(ledger);
    }
    protected LedgerPlayerCommand(LedgerPlayerCommand command, Assetholder actor) {
        super(command, actor);
    }

    @Override
    protected final void onLedgerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (isConsole()) {
            log("콘솔에서 실핼할 수 없는 명령어입니다.");
            return;
        }
        onPlayerCommand(mainArg, argsAfterMain);
    }

    protected abstract void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain);
}
