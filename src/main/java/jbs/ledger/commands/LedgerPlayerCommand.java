package jbs.ledger.commands;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import org.bukkit.entity.Player;

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

    @Nonnull
    @Override
    protected Person getPerson() {
        assert super.getPerson() != null;
        return super.getPerson();
    }

    @Nonnull
    @Override
    protected Assetholder getActor() {
        assert super.getActor() != null;
        return super.getActor();
    }

    @Nonnull
    @Override
    protected Player getPlayer() {
        assert super.getPlayer() != null;
        return super.getPlayer();
    }

    @Override
    protected final void onLedgerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (isConsole()) {
            log("콘솔에서 실행할 수 없는 명령어입니다.");
            return;
        }
        onPlayerCommand(mainArg, argsAfterMain);
    }

    protected abstract void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain);
}
