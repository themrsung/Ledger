package jbs.ledger.commands.actions;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.navigation.TeleportRequest;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * A contextual accept/deny/cancel command. Can be used to handle any offer or request.
 */
public final class HandleOffersCommand extends LedgerPlayerCommand {
    public HandleOffersCommand(Ledger ledger) {
        super(ledger);
    }
    public HandleOffersCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    private boolean yes = false;
    private boolean cancel = false;

    public void onAcceptCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        yes = true;
        onPlayerCommand(mainArg, argsAfterMain);
    }

    public void onDenyCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        yes = false;
        onPlayerCommand(mainArg, argsAfterMain);
    }

    public void onCancelCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        cancel = true;
        onPlayerCommand(mainArg, argsAfterMain);
    }

    private void acceptOrDeny(LedgerCommandKeywords.AcceptableAction action, @Nullable Assetholder sender) {
        switch (action) {
            case TELEPORT:
                if (!isSelf()) {
                    getMessenger().commandOnlyExecutableByOneself();
                    return;
                }

                if (cancel) {
                    ArrayList<TeleportRequest> trsent = getState().getTeleportRequestsBySender(getPerson());
                    if (trsent.size() < 1) {
                        getMessenger().noTeleportRequestsSent();
                        return;
                    } else if (trsent.size() == 1) {
                        trsent.get(0).onDeclined(getState());
                        return;
                    } else {
                        for (TeleportRequest r : trsent) {
                            if (sender == null) {
                                r.onDeclined(getState());
                            } else if (sender.equals(r.to)) {
                                r.onDeclined(getState());
                            }
                        }
                    }

                    return;
                }

                ArrayList<TeleportRequest> tr = getState().getTeleportRequestsByRecipient(getPerson());
                if (tr.size() < 1) {
                    getMessenger().noTeleportRequestsReceived();
                    return;
                } else if (tr.size() == 1) {
                    if (yes) {
                        tr.get(0).onAccepted(getState());
                        return;
                    } else {
                        tr.get(0).onDeclined(getState());
                        return;
                    }
                } else {
                    for (TeleportRequest r : tr) {
                        if (r.from.equals(sender)) {
                            if (yes) {
                               r.onAccepted(getState());
                               return;
                            } else {
                                r.onDeclined(getState());
                                return;
                            }
                        }
                    }
                }
        }
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        LedgerCommandKeywords.AcceptableAction action = null;

        if (mainArg != null) {
            // Try searching by assetholder exact
            Assetholder requestSender = getState().getAssetholder(mainArg, false, true);
            if (requestSender != null) {
                // Check if action was specified.
                if (argsAfterMain.length >= 1) {
                    action = LedgerCommandKeywords.AcceptableAction.get(argsAfterMain[0]);
                    if (action == null) {
                        getMessenger().invalidKeyword();
                        return;
                    }

                    acceptOrDeny(action, requestSender);
                    return;
                }
            }

            Assetholder h = null;

            if (argsAfterMain.length >= 1) {
                h = getState().getAssetholder(argsAfterMain[0], true, true);

                // Try searching by action
                LedgerCommandKeywords.AcceptableAction type = LedgerCommandKeywords.AcceptableAction.get(mainArg);
                if (type == LedgerCommandKeywords.AcceptableAction.TELEPORT) {
                    acceptOrDeny(LedgerCommandKeywords.AcceptableAction.TELEPORT, h);
                    return;
                }
            }
        }

        // Try searching for all requests
        if (getActor() instanceof Person) {
            ArrayList<TeleportRequest> tpRequests = getState().getTeleportRequestsByRecipient(getPerson());
            if (tpRequests.size() >= 1) {
                if (yes) tpRequests.get(0).onAccepted(getState());
                else tpRequests.get(0).onDeclined(getState());
                return;
            }
        }

        getMessenger().noAcceptableOfferReceived();
    }
}

