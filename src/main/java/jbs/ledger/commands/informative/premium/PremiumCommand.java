package jbs.ledger.commands.informative.premium;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import org.apache.commons.lang3.time.DateUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

public final class PremiumCommand extends LedgerPlayerCommand {
    public PremiumCommand(Ledger ledger) {
        super(ledger);
    }
    public PremiumCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (!(getActor() instanceof Person)) {
            getMessenger().commandOnlyExecutableByPlayer();
            return;
        }

        if (mainArg != null) {
            if (LedgerCommandKeywords.ADD.contains(mainArg)) {
                if (!getPlayer().isOp()) {
                    getMessenger().insufficientPermissions();
                    return;
                }

                if (argsAfterMain.length >= 1) {
                    Person p = getState().getPerson(argsAfterMain[0]);
                    if (p == null) {
                        getMessenger().assetholderNotFound();
                        return;
                    }

                    if (argsAfterMain.length >= 2) {
                        try {
                            int days = Integer.parseInt(argsAfterMain[1]);
                            if (days < 1) {
                                getMessenger().invalidNumber();
                                return;
                            }

                            Date date = p.getPremiumExpiration() != null ? p.getPremiumExpiration() : new Date();
                            Date newDate = DateUtils.addDays(date, days);

                            p.setPremiumExpiration(newDate);
                            getMessenger().premiumDaysAdded(p);
                            return;
                        } catch (NumberFormatException e) {
                            getMessenger().invalidNumber();
                            return;
                        }
                    } else {
                        p.setPremium(true);
                        p.setPremiumExpiration(null);
                        getMessenger().lifetimePremiumSet(p);
                        return;
                    }
                }
            } else if (LedgerCommandKeywords.DELETE.contains(mainArg)) {
                if (!getPlayer().isOp()) {
                    getMessenger().insufficientPermissions();
                    return;
                }

                if (argsAfterMain.length >= 2) {
                    Person p = getState().getPerson(argsAfterMain[0]);
                    if (p == null) {
                        getMessenger().assetholderNotFound();
                        return;
                    }

                    try {
                        int days = Integer.parseInt(argsAfterMain[1]);
                        if (days < 1) {
                            getMessenger().invalidNumber();
                            return;
                        }

                        Date date = p.getPremiumExpiration() != null ? p.getPremiumExpiration() : new Date();
                        Date newDate = DateUtils.addDays(date, -days);

                        p.setPremiumExpiration(newDate);

                        getMessenger().premiumDaysRemoved(p);
                        return;
                    } catch (NumberFormatException e) {
                        getMessenger().invalidNumber();
                        return;
                    }
                } else {
                    Person p = getState().getPerson(argsAfterMain[0]);
                    if (p == null) {
                        getMessenger().assetholderNotFound();;
                        return;
                    }

                    p.setPremium(false);
                    p.setPremiumExpiration(null);
                    getMessenger().premiumUnset(p);
                    return;
                }
            }
        }

        getMessenger().premiumInformation((Person) getActor());
    }
}
