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
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.classes.meetings.board.*;
import jbs.ledger.classes.meetings.shareholder.*;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.events.transfers.AssetTransferCause;
import jbs.ledger.events.transfers.basic.CashTransferredEvent;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.organizations.corporate.Board;
import jbs.ledger.organizations.sovereign.Legislature;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
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

        if (onCreateAssetholder(mainArg, argsAfterMain)) return;
        if (onCreateVote(mainArg, argsAfterMain)) return;

        getMessenger().unknownError();
        return;
    }

    private boolean onCreateAssetholder(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (argsAfterMain.length < 3) {
            getMessenger().insufficientArgs();
            return false;
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
            return false;
        }

        String symbol = argsAfterMain[0].toUpperCase();
        if (!symbol.matches("^[A-Z]*$")) {
            getMessenger().invalidSymbol();
            return false;
        }

        if (symbol.length() > 3) {
            getMessenger().symbolTooLong();
            return false;
        }

        Assetholder holder = getState().getAssetholder(symbol, false, false);
        if (holder != null) {
            getMessenger().symbolInUse();
            return false;
        }

        String name = argsAfterMain[1];
        if (name.length() > 20) {
            getMessenger().nameTooLong();
            return false;
        }

        Cash capital = Cash.fromInput(argsAfterMain[2], getState());

        if (type.isFoundation() || type.isSovereign()) {
            if (!getActor().getCash().contains(capital)) {
                getMessenger().insufficientCash();
                return false;
            }

            if (type.isFoundation()) {
                Foundation f = new Foundation(
                        UUID.randomUUID(),
                        name,
                        symbol
                );

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getActor(),
                        f,
                        capital,
                        AssetTransferCause.FOUNDATION_CREATED
                ));

                f.getBoard().addMember(getPerson());
                f.getBoard().setRepresentative(getPerson());
                getMessenger().foundationCreated();
                return true;
            } else if (type == AssetholderType.FEDERATION) {
                if (!(getActor() instanceof Nation)) {
                    getMessenger().commandOnlyExecutableByNations();
                    return false;
                }

                Federation f = new Federation(
                        UUID.randomUUID(),
                        name,
                        symbol
                );

                Nation actor = (Nation) getActor();

                f.addMember(actor);
                f.setRepresentative(actor);

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getActor(),
                        f,
                        capital,
                        AssetTransferCause.FEDERATION_CREATED
                ));

                getMessenger().federationCreated();
                return true;
            } else {
                if (!(getActor() instanceof Person) || !getActor().equals(getPerson())) {
                    getMessenger().commandOnlyExecutableByOneself();
                    return false;
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
                    return false;
                }

                n.addMember(getPerson());
                n.setRepresentative(getPerson());

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getActor(),
                        n,
                        capital,
                        AssetTransferCause.NATION_CREATED
                ));

                getState().addAssetholder(n);
                getMessenger().nationCreated();
                return true;
            }
        } else if (type.isCorporation()) {
            if (argsAfterMain.length < 4) {
                getMessenger().insufficientArgs();
                return false;
            }

            try {
                long shareCount = Long.parseLong(argsAfterMain[3]);
                if (shareCount < 1) {
                    getMessenger().invalidNumber();
                    return false;
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
                    return false;
                }

                corp.getMembers().add(getPerson());
                corp.getBoard().addMember(getPerson());
                corp.getBoard().setRepresentative(getPerson());

                Stock stocks = new Stock(symbol, shareCount);
                getActor().getStocks().add(stocks);

                Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                        getActor(),
                        corp,
                        capital,
                        AssetTransferCause.CORPORATION_CREATED
                ));

                getState().addAssetholder(corp);
                getMessenger().corporationCreated();
                return true;
            } catch (NumberFormatException e) {
                getMessenger().invalidNumber();
                return false;
            }
        } else if (type.isTrust()) {
            if (argsAfterMain.length < 5) {
                getMessenger().insufficientArgs();
                return false;
            }

            Assetholder trustee = getState().getAssetholder(argsAfterMain[3], true, true);
            Assetholder beneficiary = getState().getAssetholder(argsAfterMain[4], true, true);

            if (trustee == null || beneficiary == null) {
                getMessenger().assetholderNotFound();
                return false;
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
                return false;
            }



            Bukkit.getPluginManager().callEvent(new CashTransferredEvent(
                    getActor(),
                    t,
                    capital,
                    AssetTransferCause.TRUST_CREATED
            ));


            t.setTrustee(trustee);
            t.setBeneficiary(beneficiary);

            getState().addAssetholder(t);
            getMessenger().trustCreated();
            return true;
        }

        return false;
    }
    private boolean onCreateVote(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 2) return false;
        if (!LedgerCommandKeywords.VOTE.contains(mainArg)) return false;

        Assetholder electorate = getState().getAssetholder(argsAfterMain[0], true, true);
        if (electorate == null) {
            getMessenger().assetholderNotFound();
            return false;
        }

        try {
            MeetingType type = MeetingType.valueOf(argsAfterMain[1].toUpperCase());

            if (type.isShareholder()) {
                if (!(electorate instanceof Corporate)) {
                    getMessenger().unsupportedMeetingType();
                    return false;
                }

                Corporate corp = (Corporate) electorate;
                if (!(getActor().getStocks().contains(corp.getSymbol()))) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

                switch (type) {
                    case SHAREHOLDER_HIRE_CEO:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        Person ceo = getState().getPerson(argsAfterMain[2]);
                        if (ceo == null) {
                            getMessenger().assetholderNotFound();
                            return false;
                        }

                        if (corp.getRepresentative() != null ) {
                            getMessenger().ceoPositionNotVacant();
                            return false;
                        }

                        for (Meeting<?> m : corp.getOpenMeetings()) {
                            if (m.getType() == MeetingType.SHAREHOLDER_HIRE_CEO) {
                                getMessenger().sameMeetingAlreadyOpen();
                                return false;
                            }
                        }

                        corp.addOpenMeeting(HireCeoMeeting.newMeeting(corp, getState(), ceo));
                        getMessenger().meetingProposed();
                        return true;
                    case SHAREHOLDER_FIRE_CEO:
                        if (corp.getRepresentative() == null) {
                            getMessenger().ceoPositionAlreadyVacant();
                            return false;
                        }

                        corp.addOpenMeeting(FireCeoMeeting.newMeeting(corp, getState()));
                        getMessenger().meetingProposed();
                        return true;
                    case SHAREHOLDER_HIRE_DIRECTOR:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        Person directorToHire = getState().getPerson(argsAfterMain[2]);
                        if (directorToHire == null) {
                            getMessenger().assetholderNotFound();
                            return false;
                        }

                        if (corp.getBoard().getMembers().contains(directorToHire)) {
                            getMessenger().personAlreadyDirector();
                            return false;
                        }

                        corp.addOpenMeeting(HireDirectorMeeting.newMeeting(corp, getState(), directorToHire));
                        getMessenger().meetingProposed();
                        return true;
                    case SHAREHOLDER_FIRE_DIRECTOR:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        Person directorToFire = getState().getPerson(argsAfterMain[2]);
                        if (directorToFire == null) {
                            getMessenger().assetholderNotFound();
                            return false;
                        }

                        if (!corp.getBoard().getMembers().contains(directorToFire)) {
                            getMessenger().personNotDirector();
                            return false;
                        }

                        corp.addOpenMeeting(FireDirectorMeeting.newMeeting(corp, getState(), directorToFire));
                        getMessenger().meetingProposed();
                        return true;
                    case SHAREHOLDER_CHANGE_NAME:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        String newName = String.join(" ", Arrays.copyOfRange(argsAfterMain, 2, argsAfterMain.length));

                        if (newName.length() > 20) {
                            getMessenger().nameTooLong();
                            return false;
                        }

                        corp.addOpenMeeting(ChangeNameMeeting.newMeeting(corp, getState(), newName));
                        getMessenger().meetingProposed();
                        return true;
                    case SHAREHOLDER_LIQUIDATE:
                        corp.addOpenMeeting(LiquidationMeeting.newMeeting(corp, getState()));
                        getMessenger().meetingProposed();
                        return true;
                }

            } else if (type.isBoard()) {
                if (!(electorate instanceof Corporate)) {
                    getMessenger().unsupportedMeetingType();
                    return false;
                }

                Corporate corp = (Corporate) electorate;
                Board board = corp.getBoard();

                if (!isSelf()) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

                if (!board.getMembers().contains(getPerson())) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

                switch (type) {
                    case BOARD_CASH_DIVIDEND:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        try {
                            double dps = Double.parseDouble(argsAfterMain[2]);

                            if (dps <= 0d) {
                                getMessenger().invalidMoney();
                                return false;
                            }

                            board.addOpenMeeting(CashDividendInitiationMeeting.newMeeting(corp, dps));
                            getMessenger().meetingProposed();
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case BOARD_STOCK_DIVIDEND:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        try {
                            long dividendShares = Long.parseLong(argsAfterMain[2]);

                            if (dividendShares <= 0L) {
                                getMessenger().invalidQuantity();
                                return false;
                            }

                            board.addOpenMeeting(StockDividendInitiationMeeting.newMeeting(corp, dividendShares));
                            getMessenger().meetingProposed();
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case BOARD_STOCK_SPLIT:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        try {
                            long sharesPerShare = Long.parseLong(argsAfterMain[2]);

                            if (sharesPerShare <= 0L) {
                                getMessenger().invalidQuantity();
                                return false;
                            }

                            board.addOpenMeeting(StockSplitInitiationMeeting.newMeeting(corp, sharesPerShare));
                            getMessenger().meetingProposed();
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case BOARD_STOCK_ISSUE:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        try {
                            long issueShares = Long.parseLong(argsAfterMain[2]);

                            if (issueShares <= 0L) {
                                getMessenger().invalidQuantity();
                                return false;
                            }

                            board.addOpenMeeting(StockIssueInitiationMeeting.newMeeting(corp, issueShares));
                            getMessenger().meetingProposed();
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case BOARD_STOCK_RETIRE:
                        if (argsAfterMain.length < 3) {
                            getMessenger().insufficientArgs();
                            return false;
                        }

                        try {
                            long retireShares = Long.parseLong(argsAfterMain[2]);

                            if (retireShares <= 0L) {
                                getMessenger().invalidQuantity();
                                return false;
                            }

                            board.addOpenMeeting(StockRetireInitiationMeeting.newMeeting(corp, retireShares));
                            getMessenger().meetingProposed();
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }

                        // TODO BOARD_BOND_ISSUE
                }

            } else if (type.isParliament()) {
                if (!(electorate instanceof ParliamentaryRepublic)) {
                    getMessenger().unsupportedMeetingType();
                    return false;
                }

                ParliamentaryRepublic corp = (ParliamentaryRepublic) electorate;
                Legislature parliament = corp.getLegislature();

                if (!isSelf()) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

                switch (type) {
                    case PARLIAMENT_CHANGE_LAW:

                    case PARLIAMENT_NEW_LAW:
                }

            } else if (type.isSenate()) {
                if (!(electorate instanceof PresidentialRepublic)) {
                    getMessenger().unsupportedMeetingType();
                    return false;
                }

                PresidentialRepublic corp = (PresidentialRepublic) electorate;
                Legislature senate = corp.getLegislature();

                if (!isSelf()) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

            } else if (type.isFederation()) {
                if (!(electorate instanceof Federation)) {
                    getMessenger().unsupportedMeetingType();
                    return false;
                }

                Federation corp = (Federation) electorate;

                if (!(getActor() instanceof Sovereign)) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

                Sovereign sov = (Sovereign) getActor();
                if (!corp.getMembers().contains(sov)) {
                    getMessenger().noRightsToProposeMeeting();
                    return false;
                }

                // Propose meeting

            }

            getMessenger().unknownError();
            return false;
        } catch (IllegalArgumentException e) {
            getMessenger().invalidKeyword();
            return false;
        }
    }
}
