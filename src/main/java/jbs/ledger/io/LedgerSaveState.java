package jbs.ledger.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jbs.ledger.classes.Assetholder;
import jbs.ledger.io.types.accounts.AssetholderData;
import jbs.ledger.state.LedgerState;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The save state os LedgerState.
 * Ledger only saves Accounts. Use your own IO system to save other data.
 */
public final class LedgerSaveState {
    public static String PATH = "plugins/Ledger";

    // Saving
    public void save() {
        File path = new File(PATH);
        if (!path.mkdirs() && !path.exists()) {
            Bukkit.getLogger().info("[Ledger] Error creating plugin directory");
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

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        for (AssetholderData data : assetholders) {
            try {
                File f = new File(PATH + "/assetholders/" + data.uniqueId.toString() + ".yml");
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
    }
    public ArrayList<AssetholderData> assetholders;

    // Loading
    @Nullable
    public static LedgerSaveState load() {
        LedgerSaveState state = new LedgerSaveState();

        File path = new File(PATH);
        if (!path.exists()) {
            return null;
        }

        File assetholderFiles = new File(PATH + "/assetholders");
        if (!assetholderFiles.exists()) {
            return null;
        }

        File[] files = assetholderFiles.listFiles();
        if (files == null) {
            return null;
        }


        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        for (File f : files) {
            try {
                AssetholderData data = mapper.readValue(f, AssetholderData.class);
                if (data != null) {
                    state.assetholders.add(data);
                }

            } catch (IOException e) {
                Bukkit.getLogger().info("[Ledger] Error loading file: " + f.getName());
            }
        }

        return state;
    }

    private LedgerSaveState() {
        this.assetholders = new ArrayList<>();
    }
}
