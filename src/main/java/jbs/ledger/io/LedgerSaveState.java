package jbs.ledger.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
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
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.config.LedgerConfig;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The save state of LedgerState.
 */
public final class LedgerSaveState {
    public static String PATH = "plugins/Ledger";

    // Saving
    public void save() {
        File path = new File(PATH);
        if (!path.mkdirs() && !path.exists()) {
            Bukkit.getLogger().info("[Ledger] Error creating plugin directory");
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        File configFile = new File(PATH + "/config.yml");
        try {
            mapper.writeValue(configFile, config);
        } catch (IOException e) {
            Bukkit.getLogger().info("[Ledger] Error saving config file.");
        }

        File assetholderFiles = new File(PATH + "/assetholders");
        if (!assetholderFiles.mkdirs() && !assetholderFiles.exists()) {
            Bukkit.getLogger().info("[Ledger] Error creating assetholder directory");
        }

        try {
            FileUtils.cleanDirectory(assetholderFiles);
        } catch (IOException e) {
            Bukkit.getLogger().info("[Ledger] Error cleaning assetholder directory");
        }

        for (AssetholderData data : assetholders) {
            try {
                File f = new File(PATH + "/assetholders/" + data.uniqueId.toString() + "." + data.type.toString() + ".yml");
                mapper.writeValue(f, data);
            } catch (IOException e) {
                Bukkit.getLogger().info("[Ledger] Error saving data: " + e.getMessage());
            }
        }
    }

    public LedgerSaveState(LedgerState state) {
        this.assetholders = new ArrayList<>();

        for (Assetholder holder : state.getAssetholders()) {
            assetholders.add(holder.toData());
        }

        this.config = state.getConfig();
    }
    public ArrayList<AssetholderData> assetholders;
    public LedgerConfig config;

    // Loading
    @Nullable
    public static LedgerSaveState load() {
        LedgerSaveState state = new LedgerSaveState();

        File path = new File(PATH);
        if (!path.exists()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        File configFile = new File(PATH + "/config.yml");
        if (configFile.exists()) {
            try {
                state.config = mapper.readValue(configFile, LedgerConfig.class);
            } catch (IOException e) {
                Bukkit.getLogger().info("[Ledger] Error loading config file.");
            }
        }


        File assetholderFiles = new File(PATH + "/assetholders");
        if (!assetholderFiles.exists()) {
            return null;
        }

        File[] files = assetholderFiles.listFiles();
        if (files == null) {
            return null;
        }





        for (File f : files) {
            try {
                String[] nameSplit = f.getName().split("\\.");

                if (nameSplit.length != 3) {
                    throw new IllegalArgumentException("Error");
                }

                AssetholderType type = AssetholderType.valueOf(nameSplit[1].toUpperCase());

                switch (type) {
                    case PERSON:
                        state.assetholders.add(mapper.readValue(f, PersonData.class));
                        break;

                    case CONSTRUCTION_COMPANY:
                        state.assetholders.add(mapper.readValue(f, ConstructionCompanyData.class));
                        break;

                    case MANUFACTURER:
                        state.assetholders.add(mapper.readValue(f, ManufacturerData.class));
                        break;

                    case MERCHANT:
                        state.assetholders.add(mapper.readValue(f, MerchantData.class));
                        break;

                    case PUBLISHER:
                        state.assetholders.add(mapper.readValue(f, PublisherData.class));
                        break;

                    case LAW_FIRM:
                        state.assetholders.add(mapper.readValue(f, LawFirmData.class));
                        break;

                    case COMPANY:
                        state.assetholders.add(mapper.readValue(f, CompanyData.class));
                        break;

                    case FOUNDATION:
                        state.assetholders.add(mapper.readValue(f, FoundationData.class));
                        break;

                    case BANK:
                        state.assetholders.add(mapper.readValue(f, BankData.class));
                        break;

                    case CREDIT_CARD_COMPANY:
                        state.assetholders.add(mapper.readValue(f, CreditCardCompanyData.class));
                        break;

                    case FOREIGN_EXCHANGE:
                        state.assetholders.add(mapper.readValue(f, ForexData.class));
                        break;

                    case FUTURES_EXCHANGE:
                        state.assetholders.add(mapper.readValue(f, FuturesExchangeData.class));
                        break;

                    case SECURITIES_EXCHANGE:
                        state.assetholders.add(mapper.readValue(f, SecuritiesExchangeData.class));
                        break;

                    case PRIVATE_MILITARY:
                        state.assetholders.add(mapper.readValue(f, PrivateMilitaryData.class));
                        break;

                    case INVESTMENT_TRUST:
                        state.assetholders.add(mapper.readValue(f, InvestmentTrustData.class));
                        break;

                    case REAL_ESTATE_TRUST:
                        state.assetholders.add(mapper.readValue(f, RealEstateTrustData.class));
                        break;

                    case PRESIDENTIAL_REPUBLIC:
                        state.assetholders.add(mapper.readValue(f, PresidentialRepublicData.class));
                        break;

                    case PARLIAMENTARY_REPUBLIC:
                        state.assetholders.add(mapper.readValue(f, ParliamentaryRepublicData.class));
                        break;

                    case PRINCIPALITY:
                        state.assetholders.add(mapper.readValue(f, PrincipalityData.class));
                        break;

                    case FEDERATION:
                        state.assetholders.add(mapper.readValue(f, FederationData.class));
                        break;

                    case DISTILLERY:
                        state.assetholders.add(mapper.readValue(f, DistilleryData.class));
                        break;
                }

            } catch (IOException e) {
                Bukkit.getLogger().info("[Ledger] Error loading file: " + f.getName());
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().info("[Ledger] Invalid assetholder type in file:" + f.getName());
            }
        }

        return state;
    }

    private LedgerSaveState() {
        this.assetholders = new ArrayList<>();
    }
}
