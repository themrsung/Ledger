package jbs.ledger.commands.actions.create;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.corporations.finance.*;
import jbs.ledger.assetholders.corporations.general.*;
import jbs.ledger.assetholders.corporations.legal.LawFirm;
import jbs.ledger.assetholders.corporations.special.PrivateMilitary;
import jbs.ledger.assetholders.corporations.special.SovereignCorporation;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.assetholders.sovereignties.nations.ParliamentaryRepublic;
import jbs.ledger.assetholders.sovereignties.nations.PresidentialRepublic;
import jbs.ledger.assetholders.sovereignties.nations.Principality;
import jbs.ledger.assetholders.trusts.InvestmentTrust;
import jbs.ledger.assetholders.trusts.RealEstateTrust;
import jbs.ledger.assetholders.trusts.Trust;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public final class CreateCommand extends LedgerPlayerCommand {
    public CreateCommand(Ledger ledger) {
        super(ledger);
    }
    public CreateCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (argsAfterMain.length < 3) {
            getMessenger().insufficientArgs();
            return;
        }

        AssetholderType type = null;

        if (LedgerCommandKeywords.COMPANY.contains(mainArg)) {
            type = AssetholderType.COMPANY;
        } else if (LedgerCommandKeywords.CONSTRUCTION_COMPANY.contains(mainArg)) {
            type = AssetholderType.CONSTRUCTION_COMPANY;
        } else if (LedgerCommandKeywords.MANUFACTURER.contains(mainArg)) {
            type = AssetholderType.MANUFACTURER;
        } else if (LedgerCommandKeywords.MERCHANT.contains(mainArg)) {
            type = AssetholderType.MERCHANT;
        } else if (LedgerCommandKeywords.PUBLISHER.contains(mainArg)) {
            type = AssetholderType.PUBLISHER;
        } else if (LedgerCommandKeywords.DISTILLERY.contains(mainArg)) {
            type = AssetholderType.DISTILLERY;
        } else if (LedgerCommandKeywords.BANK.contains(mainArg)) {
            type = AssetholderType.BANK;
        } else if (LedgerCommandKeywords.CREDIT_CARD_COMPANY.contains(mainArg)) {
            type = AssetholderType.CREDIT_CARD_COMPANY;
        } else if (LedgerCommandKeywords.FOREX.contains(mainArg)) {
            type = AssetholderType.FOREIGN_EXCHANGE;
        } else if (LedgerCommandKeywords.FUTURES_EXCHANGE.contains(mainArg)) {
            type = AssetholderType.FUTURES_EXCHANGE;
        } else if (LedgerCommandKeywords.SECURITIES_EXCHANGE.contains(mainArg)) {
            type = AssetholderType.SECURITIES_EXCHANGE;
        } else if (LedgerCommandKeywords.LAW_FIRM.contains(mainArg)) {
            type = AssetholderType.LAW_FIRM;
        } else if (LedgerCommandKeywords.PRIVATE_MILITARY.contains(mainArg)) {
            type = AssetholderType.PRIVATE_MILITARY;
        } else if (LedgerCommandKeywords.FOUNDATION.contains(mainArg)) {
            type = AssetholderType.FOUNDATION;
        } else if (LedgerCommandKeywords.PRESIDENTIAL_REPUBLIC.contains(mainArg)) {
            type = AssetholderType.PRESIDENTIAL_REPUBLIC;
        } else if (LedgerCommandKeywords.PARLIAMENTARY_REPUBLIC.contains(mainArg)) {
            type = AssetholderType.PARLIAMENTARY_REPUBLIC;
        } else if (LedgerCommandKeywords.PRINCIPALITY.contains(mainArg)) {
            type = AssetholderType.PRINCIPALITY;
        } else if (LedgerCommandKeywords.FEDERATION.contains(mainArg)) {
            type = AssetholderType.FEDERATION;
        } else if (LedgerCommandKeywords.INVESTMENT_TRUST.contains(mainArg)) {
            type = AssetholderType.INVESTMENT_TRUST;
        } else if (LedgerCommandKeywords.REAL_ESTATE_TRUST.contains(mainArg)) {
            type = AssetholderType.REAL_ESTATE_TRUST;
        } else if (LedgerCommandKeywords.SOVEREIGN_CORPORATION.contains(mainArg)) {
            type = AssetholderType.SOVEREIGN_CORPORATION;
        }

        if (type == null) {
            getMessenger().invalidKeyword();
            return;
        }

        String symbol = argsAfterMain[0].toUpperCase();
        if (!symbol.matches("^[A-Z]*$")) {
            getMessenger().invalidSymbol();
            return;
        }

        if (symbol.length() > 3) {
            getMessenger().symbolTooLong();
            return;
        }

        Assetholder holder = getState().getAssetholder(symbol, false, false);
        if (holder != null) {
            getMessenger().symbolInUse();
            return;
        }

        String name = argsAfterMain[1];
        if (name.length() > 20) {
            getMessenger().nameTooLong();
            return;
        }

        Cash capital = Cash.fromInput(argsAfterMain[2], getState());

        if (type.isFoundation() || type.isSovereign()) {
            if (!getActor().getCash().contains(capital)) {
                getMessenger().insufficientCash();
                return;
            }

            if (type.isFoundation()) {
                Foundation f = new Foundation(
                        UUID.randomUUID(),
                        name,
                        symbol
                );

                f.getBoard().addMember(getPerson());
                f.getBoard().setRepresentative(getPerson());
                getMessenger().foundationCreated();
                return;
            } else if (type == AssetholderType.FEDERATION) {
                if (!(getActor() instanceof Nation)) {
                    getMessenger().commandOnlyExecutableByNations();
                    return;
                }

                Federation f = new Federation(
                        UUID.randomUUID(),
                        name,
                        symbol
                );

                Nation actor = (Nation) getActor();

                f.addMember(actor);
                f.setRepresentative(actor);
                // TODO Send money to foundation from actor upon creation

                getMessenger().federationCreated();
                return;
            } else {
                if (!(getActor() instanceof Person) || !getActor().equals(getPerson())) {
                    getMessenger().commandOnlyExecutableByOneself();
                    return;
                }

                Nation n;

                switch (type) {
                    case PRESIDENTIAL_REPUBLIC:
                        n = new PresidentialRepublic(
                                UUID.randomUUID(),
                                name,
                                symbol
                        );

                        break;
                    case PARLIAMENTARY_REPUBLIC:
                        n = new ParliamentaryRepublic(
                                UUID.randomUUID(),
                                name,
                                symbol
                        );

                        break;
                    case PRINCIPALITY:
                        n = new Principality(
                                UUID.randomUUID(),
                                name,
                                symbol
                        );

                        break;
                    default:
                        n = null;

                }

                if (n == null) {
                    getMessenger().unknownError();
                    return;
                }
                // TODO Send money to nation from actor upon creation

                n.addMember(getPerson());
                n.setRepresentative(getPerson());

                getState().addAssetholder(n);
                getMessenger().nationCreated();
                return;
            }
        } else if (type.isCorporation()) {
            if (argsAfterMain.length < 4) {
                getMessenger().insufficientArgs();
                return;
            }

            try {
                long shareCount = Long.parseLong(argsAfterMain[3]);
                if (shareCount < 1) {
                    getMessenger().invalidNumber();
                    return;
                }

                Corporation corp = null;
                String currency = capital.getSymbol();

                UUID u = UUID.randomUUID();

                switch (type) {
                    case BANK:
                        corp = new Bank(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case CREDIT_CARD_COMPANY:
                        corp = new CreditCardCompany(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case FOREIGN_EXCHANGE:
                        corp = new ForeignExchange(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case FUTURES_EXCHANGE:
                        corp = new FuturesExchange(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case SECURITIES_EXCHANGE:
                        corp = new SecuritiesExchange(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case COMPANY:
                        corp = new Company(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case CONSTRUCTION_COMPANY:
                        corp = new ConstructionCompany(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case DISTILLERY:
                        corp = new Distillery(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case MANUFACTURER:
                        corp = new Manufacturer(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case MERCHANT:
                        corp = new Merchant(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case PUBLISHER:
                        corp = new Publisher(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case LAW_FIRM:
                        corp = new LawFirm(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case PRIVATE_MILITARY:
                        corp = new PrivateMilitary(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;

                    case SOVEREIGN_CORPORATION:
                        corp = new SovereignCorporation(
                                u,
                                name,
                                symbol,
                                currency,
                                capital,
                                shareCount
                        );
                        break;
                }

                if (corp == null) {
                    getMessenger().unknownError();
                    return;
                }

                // TODO Send money to corporation from actor upon creation

                corp.getMembers().add(getPerson());
                corp.getBoard().addMember(getPerson());
                corp.getBoard().setRepresentative(getPerson());

                Stock stocks = new Stock(symbol, shareCount);
                getActor().getStocks().add(stocks);

                getState().addAssetholder(corp);
                getMessenger().corporationCreated();
                return;
            } catch (NumberFormatException e) {
                getMessenger().invalidNumber();
                return;
            }
        } else if (type.isTrust()) {
            if (argsAfterMain.length < 5) {
                getMessenger().insufficientArgs();
                return;
            }

            Assetholder trustee = getState().getAssetholder(argsAfterMain[3], true, true);
            Assetholder beneficiary = getState().getAssetholder(argsAfterMain[4], true, true);

            if (trustee == null || beneficiary == null) {
                getMessenger().assetholderNotFound();
                return;
            }

            Trust t = null;

            switch (type) {
                case INVESTMENT_TRUST:
                    t = new InvestmentTrust(
                            UUID.randomUUID(),
                            name,
                            symbol,
                            getActor()
                    );
                    break;

                case REAL_ESTATE_TRUST:
                    t = new RealEstateTrust(
                            UUID.randomUUID(),
                            name,
                            symbol,
                            getActor()
                    );
                    break;
            }

            if (t == null) {
                getMessenger().unknownError();
                return;
            }
            // TODO Send money to trust from actor upon creation


            t.setTrustee(trustee);
            t.setBeneficiary(beneficiary);

            getState().addAssetholder(t);
            getMessenger().trustCreated();
            return;
        }

        getMessenger().unknownError();
        return;
    }
}
