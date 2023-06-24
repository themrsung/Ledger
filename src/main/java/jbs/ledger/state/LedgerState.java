package jbs.ledger.state;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.finance.*;
import jbs.ledger.assetholders.corporations.general.*;
import jbs.ledger.assetholders.corporations.legal.LawFirm;
import jbs.ledger.assetholders.corporations.special.PrivateMilitary;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.assetholders.sovereignties.nations.ParliamentaryRepublic;
import jbs.ledger.assetholders.sovereignties.nations.PresidentialRepublic;
import jbs.ledger.assetholders.sovereignties.nations.Principality;
import jbs.ledger.assetholders.trusts.InvestmentTrust;
import jbs.ledger.assetholders.trusts.RealEstateTrust;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.io.types.assetholders.AssetholderData;
import jbs.ledger.io.types.assetholders.corporations.finance.*;
import jbs.ledger.io.types.assetholders.corporations.general.*;
import jbs.ledger.io.types.assetholders.corporations.legal.LawFirmData;
import jbs.ledger.io.types.assetholders.corporations.special.PrivateMilitaryData;
import jbs.ledger.io.types.assetholders.foundations.FoundationData;
import jbs.ledger.io.types.assetholders.person.PersonData;
import jbs.ledger.io.types.assetholders.sovereignties.federations.FederationData;
import jbs.ledger.io.types.assetholders.sovereignties.nations.ParliamentaryRepublicData;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PresidentialRepublicData;
import jbs.ledger.io.types.assetholders.sovereignties.nations.PrincipalityData;
import jbs.ledger.io.types.assetholders.trusts.InvestmentTrustData;
import jbs.ledger.io.types.assetholders.trusts.RealEstateTrustData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The running state of this plugin.
 */
public final class LedgerState {
    public LedgerState() {
        this.assetholders = new ArrayList<>();
    }

    // Assetholders
    private final ArrayList<Assetholder> assetholders;

    /**
     * Gets all assetholders in existence.
     * @return Returns a copied list of assetholders.
     */
    public ArrayList<Assetholder> getAssetholders() {
        return new ArrayList<>(assetholders);
    }

    @Nullable
    public Assetholder getAssetholder(UUID holderId) {
        for (Assetholder a : getAssetholders()) {
            if (a.getUniqueId().equals(holderId)) {
                return a;
            }
        }

        return null;
    }

    /**
     * Searches for an assetholder. Searches in the following order.
     * Search is not case-sensitive.
     * Searches in the following order: tag exact -> tag contains -> name exact -> name contains
     * @param tag Tag to search for
     * @param checkContains Will do a contains() check if true
     * @param checkName Will check names if true
     * @return Returns the search result
     */
    @Nullable
    public Assetholder getAssetholder(String tag, boolean checkContains, boolean checkName) {
        ArrayList<Assetholder> holders = getAssetholders();

        for (Assetholder h : holders) {
            if (h.getSearchTag().equalsIgnoreCase(tag)) {
                return h;
            }
        }

        if (checkContains) {
            for (Assetholder h : holders) {
                if (h.getSearchTag().toLowerCase().contains(tag.toLowerCase())) {
                    return h;
                }
            }
        }

        if (checkName) {
            for (Assetholder h : holders) {
                if (h.getName().equalsIgnoreCase(tag)) {
                    return h;
                }
            }

            if (checkContains) {
                for (Assetholder h : holders) {
                    if (h.getName().toLowerCase().contains(tag.toLowerCase())) {
                        return h;
                    }
                }
            }
        }

        return null;
    }

    public void addAssetholder(Assetholder assetholder) {
        this.assetholders.add(assetholder);
    }
    public boolean removeAssetholder(Assetholder assetholder) {
        return this.assetholders.remove(assetholder);
    }

    // People
    public ArrayList<Person> getPeople() {
        ArrayList<Person> people = new ArrayList<>();;

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Person) {
                people.add((Person) a);
            }
        }

        return people;
    }
    @Nullable
    public Person getPerson(UUID uniqueId) {
        for (Person p : getPeople()) {
            if (p.getUniqueId().equals(uniqueId)) {
                return p;
            }
        }

        return null;
    }

    // Corporations
    public ArrayList<Corporate> getCorporates() {
        ArrayList<Corporate> corporates = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Corporate) {
                corporates.add((Corporate) a);
            }
        }

        return corporates;
    }

    @Nullable
    public Corporate getCorporate(UUID uniqueId) {
        for (Corporate c : getCorporates()) {
            if (c.getUniqueId().equals(uniqueId)) {
                return c;
            }
        }

        return null;
    }

    // Sovereigns
    public ArrayList<Sovereign> getSovereigns() {
        ArrayList<Sovereign> sovereigns = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Sovereign) {
                sovereigns.add((Sovereign) a);
            }
        }

        return sovereigns;
    }

    @Nullable
    public Sovereign getSovereign(UUID uniqueId) {
        for (Sovereign s : getSovereigns()) {
            if (s.getUniqueId().equals(uniqueId)) {
                return s;
            }
        }

        return null;
    }

    // Nations
    public ArrayList<Nation> getNations() {
        ArrayList<Nation> nations = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Nation) {
                nations.add((Nation) a);
            }
        }

        return nations;
    }

    @Nullable
    public Nation getNation(UUID uniqueId) {
        for (Nation n : getNations()) {
            if (n.getUniqueId().equals(uniqueId)) {
                return n;
            }
        }

        return null;
    }

    // Nations
    public ArrayList<NationMember> getNationMembers() {
        ArrayList<NationMember> members = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof NationMember) {
                members.add((NationMember) a);
            }
        }

        return members;
    }

    @Nullable
    public NationMember getNationMember(UUID uniqueid) {
        for (NationMember m : getNationMembers()) {
            if (m.getUniqueId().equals(uniqueid)) {
                return m;
            }
        }

        return null;
    }

    // Foundations
    public ArrayList<Foundation> getFoundations() {
        ArrayList<Foundation> foundations = new ArrayList<>();

        for (Assetholder a : getAssetholders()) {
            if (a instanceof Foundation) {
                foundations.add((Foundation) a);
            }
        }

        return foundations;
    }

    @Nullable
    public Foundation getFoundation(UUID uniwueId) {
        for (Foundation f : getFoundations()) {
            if (f.getUniqueId().equals(uniwueId)) {
                return f;
            }
        }

        return null;
    }


    // IO
    public LedgerState(LedgerSaveState saveState) {
        assetholders = new ArrayList<>();

        for (AssetholderData data : saveState.assetholders) {
            switch (data.type) {
                case PERSON:
                    addAssetholder(Person.getEmptyInstance(data.uniqueId));
                    break;

                case CONSTRUCTION_COMPANY:
                    addAssetholder(ConstructionCompany.getEmptyInstance(data.uniqueId));
                    break;

                case MANUFACTURER:
                    addAssetholder(Manufacturer.getEmptyInstance(data.uniqueId));
                    break;

                case MERCHANT:
                    addAssetholder(Merchant.getEmptyInstance(data.uniqueId));
                    break;

                case PUBLISHER:
                    addAssetholder(Publisher.getEmptyInstance(data.uniqueId));
                    break;

                case LAW_FIRM:
                    addAssetholder(LawFirm.getEmptyInstance(data.uniqueId));
                    break;

                case COMPANY:
                    addAssetholder(Company.getEmptyInstance(data.uniqueId));
                    break;

                case FOUNDATION:
                    addAssetholder(Foundation.getEmptyInstance(data.uniqueId));
                    break;

                case BANK:
                    addAssetholder(Bank.getEmptyInstance(data.uniqueId));
                    break;

                case CREDIT_CARD_COMPANY:
                    addAssetholder(CreditCardCompany.getEmptyInstance(data.uniqueId));
                    break;

                case FOREIGN_EXCHANGE:
                    addAssetholder(ForeignExchange.getEmptyInstance(data.uniqueId));
                    break;

                case FUTURES_EXCHANGE:
                    addAssetholder(FuturesExchange.getEmptyInstance(data.uniqueId));
                    break;

                case SECURITIES_EXCHANGE:
                    addAssetholder(SecuritiesExchange.getEmptyInstance(data.uniqueId));
                    break;

                case PRIVATE_MILITARY:
                    addAssetholder(PrivateMilitary.getEmptyInstance(data.uniqueId));
                    break;

                case INVESTMENT_TRUST:
                    addAssetholder(InvestmentTrust.getEmptyInstance(data.uniqueId));
                    break;

                case REAL_ESTATE_TRUST:
                    addAssetholder(RealEstateTrust.getEmptyInstance(data.uniqueId));
                    break;

                case PRESIDENTIAL_REPUBLIC:
                    addAssetholder(PresidentialRepublic.getEmptyInstance(data.uniqueId));
                    break;

                case PARLIAMENTARY_REPUBLIC:
                    addAssetholder(ParliamentaryRepublic.getEmptyInstance(data.uniqueId));
                    break;

                case PRINCIPALITY:
                    addAssetholder(Principality.getEmptyInstance(data.uniqueId));
                    break;

                case FEDERATION:
                    addAssetholder(Federation.getEmptyInstance(data.uniqueId));
                    break;

            }
        }

        for (Assetholder a : assetholders) {
            for (AssetholderData ad : saveState.assetholders) {
                if (a.getUniqueId().equals(ad.uniqueId)) {
                    switch (ad.type) {
                        case PERSON:
                            Person p = (Person) a;
                            PersonData pd = (PersonData) ad;
                            p.load(pd, this);
                            break;

                        case CONSTRUCTION_COMPANY:
                            ConstructionCompany cc = (ConstructionCompany) a;
                            ConstructionCompanyData ccd = (ConstructionCompanyData) ad;
                            cc.load(ccd, this);
                            break;

                        case MANUFACTURER:
                            Manufacturer m = (Manufacturer) a;
                            ManufacturerData md = (ManufacturerData) ad;
                            m.load(md, this);
                            break;

                        case MERCHANT:
                            Merchant mc = (Merchant) a;
                            MerchantData mcd = (MerchantData) ad;
                            mc.load(mcd, this);
                            break;

                        case PUBLISHER:
                            Publisher pu = (Publisher) a;
                            PublisherData pud = (PublisherData) ad;
                            pu.load(pud, this);
                            break;

                        case LAW_FIRM:
                            LawFirm lf = (LawFirm) a;
                            LawFirmData lfd = (LawFirmData) ad;
                            lf.load(lfd, this);
                            break;

                        case COMPANY:
                            Company co = (Company) a;
                            CompanyData cod = (CompanyData) ad;
                            co.load(cod, this);
                            break;

                        case FOUNDATION:
                            Foundation f = (Foundation) a;
                            FoundationData fd = (FoundationData) ad;
                            f.load(fd, this);
                            break;

                        case BANK:
                            Bank b = (Bank) a;
                            BankData bd = (BankData) ad;
                            b.load(bd, this);
                            break;

                        case CREDIT_CARD_COMPANY:
                            CreditCardCompany ccc = (CreditCardCompany) a;
                            CreditCardCompanyData cccd = (CreditCardCompanyData) ad;
                            ccc.load(cccd, this);
                            break;

                        case FOREIGN_EXCHANGE:
                            ForeignExchange forex = (ForeignExchange) a;
                            ForexData fxd = (ForexData) ad;
                            forex.load(fxd, this);
                            break;

                        case FUTURES_EXCHANGE:
                            FuturesExchange fuex = (FuturesExchange) a;
                            FuturesExchangeData fed = (FuturesExchangeData) ad;
                            fuex.load(fed, this);
                            break;

                        case SECURITIES_EXCHANGE:
                            SecuritiesExchange se = (SecuritiesExchange) a;
                            SecuritiesExchangeData sed = (SecuritiesExchangeData) ad;
                            se.load(sed, this);
                            break;

                        case PRIVATE_MILITARY:
                            PrivateMilitary pmc = (PrivateMilitary) a;
                            PrivateMilitaryData pmcd = (PrivateMilitaryData) ad;
                            pmc.load(pmcd, this);
                            break;

                        case INVESTMENT_TRUST:
                            InvestmentTrust it = (InvestmentTrust) a;
                            InvestmentTrustData itd = (InvestmentTrustData) ad;
                            it.load(itd, this);
                            break;

                        case REAL_ESTATE_TRUST:
                            RealEstateTrust ret = (RealEstateTrust) a;
                            RealEstateTrustData retd = (RealEstateTrustData) ad;
                            ret.load(retd, this);
                            break;

                        case PRESIDENTIAL_REPUBLIC:
                            PresidentialRepublic prrc = (PresidentialRepublic) a;
                            PresidentialRepublicData prrd = (PresidentialRepublicData) ad;
                            prrc.load(prrd, this);
                            break;

                        case PARLIAMENTARY_REPUBLIC:
                            ParliamentaryRepublic parc = (ParliamentaryRepublic) a;
                            ParliamentaryRepublicData pard = (ParliamentaryRepublicData) ad;
                            parc.load(pard, this);
                            break;

                        case PRINCIPALITY:
                            Principality pc = (Principality) a;
                            PrincipalityData pcd = (PrincipalityData) ad;
                            pc.load(pcd, this);
                            break;

                        case FEDERATION:
                            Federation fdr = (Federation) a;
                            FederationData fdd = (FederationData) ad;
                            fdr.load(fdd, this);
                            break;
                    }
                }
            }
        }
    }


}
