package jbs.ledger.commands.informative.list;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.corporations.finance.ForeignExchange;
import jbs.ledger.assetholders.corporations.finance.FuturesExchange;
import jbs.ledger.assetholders.corporations.finance.SecuritiesExchange;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.trusts.Trust;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.interfaces.sovereignty.Sovereign;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ListCommand extends LedgerPlayerCommand {
    public ListCommand(Ledger ledger) {
        super(ledger);
    }
    public ListCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null) {
            getMessenger().listHeader();

            for (Assetholder a : getState().getAssetholders()) {
                getMessenger().assetholderListEntry(a);
            }
        } else {
            String action = mainArg.toLowerCase();

            if (LedgerCommandKeywords.MARKETS.contains(action)) {
                getMessenger().listHeader();

                for (Market<?> m : getState().getMarkets()) {
                    getMessenger().marketListEntry(m);
                }

                return;
            } else if (LedgerCommandKeywords.EXCHANGES.contains(action)) {
                getMessenger().listHeader();

                for (Corporate c : getState().getCorporates()) {
                    if ((c instanceof ForeignExchange || c instanceof FuturesExchange || c instanceof SecuritiesExchange)) {
                        getMessenger().corporateListEntry(c);
                    }
                }

                return;

            } else if (LedgerCommandKeywords.PLAYERS.contains(action)) {
                getMessenger().listHeader();

                for (Person p : getState().getPeople()) {
                    getMessenger().playerListEntry(p);
                }

                return;
            } else if (LedgerCommandKeywords.CORPORATES.contains(action)) {
                getMessenger().listHeader();

                for (Corporate c : getState().getCorporates()) {
                    getMessenger().corporateListEntry(c);
                }

                return;

            } else if (LedgerCommandKeywords.FOUNDATIONS_GENERIC.contains(action)) {
                getMessenger().listHeader();

                for (Foundation f : getState().getFoundations()) {
                    getMessenger().foundationListEntry(f);
                }

                return;

            } else if (LedgerCommandKeywords.SOVEREIGNS.contains(action)) {
                getMessenger().listHeader();

                for (Sovereign s : getState().getSovereigns()) {
                    getMessenger().sovereignListEntry(s);
                }

                return;

            } else if (LedgerCommandKeywords.TRUSTS_GENERIC.contains(action)) {
                getMessenger().listHeader();

                for (Trust t : getState().getTrusts()) {
                    getMessenger().trustListEntry(t);
                }

                return;
            }
        }

        getMessenger().invalidKeyword();
    }
}
