package jbs.ledger;

import jbs.ledger.classes.messages.DirectMessageProcessor;
import jbs.ledger.commands.actions.*;
import jbs.ledger.commands.admin.SetSpawnCommand;
import jbs.ledger.commands.administrative.DirectorsCommand;
import jbs.ledger.commands.administrative.ManageCommand;
import jbs.ledger.commands.administrative.MembersCommand;
import jbs.ledger.commands.administrative.SudoCommand;
import jbs.ledger.commands.economy.*;
import jbs.ledger.commands.informative.InformationCommand;
import jbs.ledger.commands.informative.ListCommand;
import jbs.ledger.commands.informative.NetWorthLeaderboardCommand;
import jbs.ledger.commands.informative.PremiumCommand;
import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.listeners.bookkeeping.AssetTransferHandler;
import jbs.ledger.listeners.player.PlayerPreviousLocationSetter;
import jbs.ledger.listeners.player.PlayerProfileUpdater;
import jbs.ledger.state.LedgerState;
import jbs.ledger.timers.LedgerAutoSaver;
import jbs.ledger.timers.LedgerNoteHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Ledger extends JavaPlugin {
    public static String VERSION = "1.0";

    @Override
    public void onEnable() {

        load();

        if (state == null) {
            state = new LedgerState();
        }

        enableCommands();
        registerEventListeners();
        scheduleRepeatingTasks();

        Bukkit.getLogger().info("Ledger v" + VERSION + " is loaded.");
    }

    @Override
    public void onDisable() {
        save();
    }

    private void enableCommands() {
        Objects.requireNonNull(getCommand("accept")).setExecutor(new AcceptCommand(this));
        Objects.requireNonNull(getCommand("deny")).setExecutor(new DenyCommand(this));

        Objects.requireNonNull(getCommand("sudo")).setExecutor(new SudoCommand(this));

        Objects.requireNonNull(getCommand("create")).setExecutor(new CreateCommand(this));
        Objects.requireNonNull(getCommand("manage")).setExecutor(new ManageCommand(this));
        Objects.requireNonNull(getCommand("members")).setExecutor(new MembersCommand(this));
        Objects.requireNonNull(getCommand("directors")).setExecutor(new DirectorsCommand(this));
        Objects.requireNonNull(getCommand("invite")).setExecutor(new InviteCommand(this));
        Objects.requireNonNull(getCommand("punish")).setExecutor(new PunishCommand(this));
        Objects.requireNonNull(getCommand("pardon")).setExecutor(new PardonCommand(this));
        Objects.requireNonNull(getCommand("vote")).setExecutor(new VoteCommand(this));
        Objects.requireNonNull(getCommand("kick")).setExecutor(new KickCommand(this));

        Objects.requireNonNull(getCommand("message")).setExecutor(new MessageCommand(this));
        Objects.requireNonNull(getCommand("reply")).setExecutor(new ReplyCommand(this));

        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));

        Objects.requireNonNull(getCommand("teleportaccept")).setExecutor(new TeleportAcceptCommand(this));
        Objects.requireNonNull(getCommand("teleportdeny")).setExecutor(new TeleportDenyCommand(this));
        Objects.requireNonNull(getCommand("teleporthererequest")).setExecutor(new TeleportHereRequestCommand(this));
        Objects.requireNonNull(getCommand("teleportrequest")).setExecutor(new TeleportRequestCommand(this));
        Objects.requireNonNull(getCommand("back")).setExecutor(new BackCommand(this));
        Objects.requireNonNull(getCommand("gps")).setExecutor(new GpsCommand(this));

        Objects.requireNonNull(getCommand("information")).setExecutor(new InformationCommand(this));
        Objects.requireNonNull(getCommand("list")).setExecutor(new ListCommand(this));
        Objects.requireNonNull(getCommand("networthleaderboard")).setExecutor(new NetWorthLeaderboardCommand(this));


        Objects.requireNonNull(getCommand("assets")).setExecutor(new AssetsCommand(this));
        Objects.requireNonNull(getCommand("liabilities")).setExecutor(new LiabilitiesCommand(this));
        Objects.requireNonNull(getCommand("cards")).setExecutor(new CardsCommand(this));
        Objects.requireNonNull(getCommand("forwards")).setExecutor(new ForwardsCommand(this));
        Objects.requireNonNull(getCommand("futures")).setExecutor(new FuturesCommand(this));
        Objects.requireNonNull(getCommand("networth")).setExecutor(new NetWorthCommand(this));
        Objects.requireNonNull(getCommand("stocks")).setExecutor(new StocksCommand(this));
        Objects.requireNonNull(getCommand("buy")).setExecutor(new BuyCommand(this));
        Objects.requireNonNull(getCommand("sell")).setExecutor(new SellCommand(this));

        Objects.requireNonNull(getCommand("pay")).setExecutor(new PayCommand(this));

        Objects.requireNonNull(getCommand("information")).setExecutor(new InformationCommand(this));
        Objects.requireNonNull(getCommand("list")).setExecutor(new ListCommand(this));
        Objects.requireNonNull(getCommand("networthleaderboard")).setExecutor(new NetWorthLeaderboardCommand(this));

        Objects.requireNonNull(getCommand("premium")).setExecutor(new PremiumCommand(this));
    }

    private void registerEventListeners() {
        new AssetTransferHandler(this);

        new PlayerProfileUpdater(this);
        new PlayerPreviousLocationSetter(this);
    }

    private void scheduleRepeatingTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerAutoSaver(this), 20, 60 * 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerNoteHandler(this), 30, 15 * 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new DirectMessageProcessor(this), 10,10);
    }

    private LedgerState state = null;
    public LedgerState getState() {
        return state;
    }

    public void save() {
        LedgerSaveState saveState = new LedgerSaveState(state);
        saveState.save();
    }

    public void load() {
        LedgerSaveState saveState = LedgerSaveState.load();

        if (saveState != null) {
            state = new LedgerState(saveState);
        }
    }
}
