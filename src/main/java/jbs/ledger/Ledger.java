package jbs.ledger;

import jbs.ledger.classes.messages.DirectMessageProcessor;
import jbs.ledger.commands.actions.invite.InviteCommand;
import jbs.ledger.commands.actions.invite.InviteCommandCompleter;
import jbs.ledger.commands.actions.kick.KickCommand;
import jbs.ledger.commands.actions.kick.KickCommandCompleter;
import jbs.ledger.commands.actions.pardon.PardonCommand;
import jbs.ledger.commands.actions.pardon.PardonCommandCompleter;
import jbs.ledger.commands.actions.punish.PunishCommand;
import jbs.ledger.commands.actions.punish.PunishCommandCompleter;
import jbs.ledger.commands.actions.teleportation.BackCommand;
import jbs.ledger.commands.actions.create.CreateCommand;
import jbs.ledger.commands.actions.create.CreateCommandCompleter;
import jbs.ledger.commands.actions.gps.GpsCommand;
import jbs.ledger.commands.actions.gps.GpsCommandCompleter;
import jbs.ledger.commands.actions.home.DeleteHomeCommand;
import jbs.ledger.commands.actions.home.HomeCommand;
import jbs.ledger.commands.actions.home.SetHomeCommand;
import jbs.ledger.commands.actions.message.MessageCommand;
import jbs.ledger.commands.actions.message.MessageCommandCompleter;
import jbs.ledger.commands.actions.offers.AcceptCommand;
import jbs.ledger.commands.actions.offers.CancelCommand;
import jbs.ledger.commands.actions.offers.DenyCommand;
import jbs.ledger.commands.actions.offers.HandleOffersCommandCompleter;
import jbs.ledger.commands.actions.reply.ReplyCommand;
import jbs.ledger.commands.actions.teleportation.SpawnCommand;
import jbs.ledger.commands.actions.teleportation.TeleportHereRequestCommand;
import jbs.ledger.commands.actions.teleportation.TeleportRequestCommand;
import jbs.ledger.commands.actions.teleportation.TeleportRequestCommandCompleter;
import jbs.ledger.commands.actions.vote.VoteCommand;
import jbs.ledger.commands.actions.vote.VoteCommandCompleter;
import jbs.ledger.commands.administrative.*;
import jbs.ledger.commands.administrative.sudo.SudoCommand;
import jbs.ledger.commands.administrative.sudo.SudoCommandCompleter;
import jbs.ledger.commands.economy.balance.BalanceCommand;
import jbs.ledger.commands.economy.balance.BalanceCommandCompleter;
import jbs.ledger.commands.economy.credit.CreditRatingCommand;
import jbs.ledger.commands.economy.credit.CreditRatingCommandCompleter;
import jbs.ledger.commands.economy.pay.PayCommand;
import jbs.ledger.commands.economy.pay.PayCommandCompleter;
import jbs.ledger.commands.economy.trading.*;
import jbs.ledger.commands.server.SetSpawnCommand;
import jbs.ledger.commands.economy.*;
import jbs.ledger.commands.informative.InformationCommand;
import jbs.ledger.commands.informative.ListCommand;
import jbs.ledger.commands.informative.NetWorthLeaderboardCommand;
import jbs.ledger.commands.informative.premium.PremiumCommand;
import jbs.ledger.io.LedgerSaveState;
import jbs.ledger.listeners.player.PlayerRespawnHandler;
import jbs.ledger.listeners.property.PropertyProtector;
import jbs.ledger.listeners.transfers.AssetTransferHandler;
import jbs.ledger.listeners.player.PlayerPreviousLocationSetter;
import jbs.ledger.listeners.player.PlayerProfileUpdater;
import jbs.ledger.state.LedgerState;
import jbs.ledger.timers.economy.MarketTicker;
import jbs.ledger.timers.governance.OfficeTermKeeper;
import jbs.ledger.timers.io.LedgerAutoSaver;
import jbs.ledger.timers.economy.NoteExpirationHandler;
import jbs.ledger.timers.premium.PremiumExpirationHandler;
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
        Objects.requireNonNull(getCommand("accept")).setTabCompleter(new HandleOffersCommandCompleter(this));

        Objects.requireNonNull(getCommand("deny")).setExecutor(new DenyCommand(this));
        Objects.requireNonNull(getCommand("deny")).setTabCompleter(new HandleOffersCommandCompleter(this));

        Objects.requireNonNull(getCommand("cancel")).setExecutor(new CancelCommand(this));
        Objects.requireNonNull(getCommand("cancel")).setTabCompleter(new HandleOffersCommandCompleter(this));

        Objects.requireNonNull(getCommand("sudo")).setExecutor(new SudoCommand(this));
        Objects.requireNonNull(getCommand("sudo")).setTabCompleter(new SudoCommandCompleter(this));

        Objects.requireNonNull(getCommand("create")).setExecutor(new CreateCommand(this));
        Objects.requireNonNull(getCommand("create")).setTabCompleter(new CreateCommandCompleter(this));

        Objects.requireNonNull(getCommand("creditrating")).setExecutor(new CreditRatingCommand(this));
        Objects.requireNonNull(getCommand("creditrating")).setTabCompleter(new CreditRatingCommandCompleter(this));

        Objects.requireNonNull(getCommand("sethome")).setExecutor(new SetHomeCommand(this));
        Objects.requireNonNull(getCommand("deletehome")).setExecutor(new DeleteHomeCommand(this));
        Objects.requireNonNull(getCommand("home")).setExecutor(new HomeCommand(this));
        Objects.requireNonNull(getCommand("back")).setExecutor(new BackCommand(this));

        Objects.requireNonNull(getCommand("invite")).setExecutor(new InviteCommand(this));
        Objects.requireNonNull(getCommand("invite")).setTabCompleter(new InviteCommandCompleter(this));

        Objects.requireNonNull(getCommand("vote")).setExecutor(new VoteCommand(this));
        Objects.requireNonNull(getCommand("vote")).setTabCompleter(new VoteCommandCompleter(this));

        Objects.requireNonNull(getCommand("kick")).setExecutor(new KickCommand(this));
        Objects.requireNonNull(getCommand("kick")).setTabCompleter(new KickCommandCompleter(this));

        Objects.requireNonNull(getCommand("manage")).setExecutor(new ManageCommand(this));
        Objects.requireNonNull(getCommand("members")).setExecutor(new MembersCommand(this));
        Objects.requireNonNull(getCommand("directors")).setExecutor(new DirectorsCommand(this));

        Objects.requireNonNull(getCommand("punish")).setExecutor(new PunishCommand(this));
        Objects.requireNonNull(getCommand("punish")).setTabCompleter(new PunishCommandCompleter(this));

        Objects.requireNonNull(getCommand("pardon")).setExecutor(new PardonCommand(this));
        Objects.requireNonNull(getCommand("pardon")).setTabCompleter(new PardonCommandCompleter(this));

        Objects.requireNonNull(getCommand("message")).setExecutor(new MessageCommand(this));
        Objects.requireNonNull(getCommand("message")).setTabCompleter(new MessageCommandCompleter(this));

        Objects.requireNonNull(getCommand("reply")).setExecutor(new ReplyCommand(this));

        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));

        Objects.requireNonNull(getCommand("teleporthererequest")).setExecutor(new TeleportHereRequestCommand(this));
        Objects.requireNonNull(getCommand("teleporthererequest")).setTabCompleter(new TeleportRequestCommandCompleter(this));

        Objects.requireNonNull(getCommand("teleportrequest")).setExecutor(new TeleportRequestCommand(this));
        Objects.requireNonNull(getCommand("teleportrequest")).setTabCompleter(new TeleportRequestCommandCompleter(this));


        Objects.requireNonNull(getCommand("gps")).setExecutor(new GpsCommand(this));
        Objects.requireNonNull(getCommand("gps")).setTabCompleter(new GpsCommandCompleter(this));

        Objects.requireNonNull(getCommand("information")).setExecutor(new InformationCommand(this));
        Objects.requireNonNull(getCommand("list")).setExecutor(new ListCommand(this));
        Objects.requireNonNull(getCommand("networthleaderboard")).setExecutor(new NetWorthLeaderboardCommand(this));

        Objects.requireNonNull(getCommand("balance")).setExecutor(new BalanceCommand(this));
        Objects.requireNonNull(getCommand("balance")).setTabCompleter(new BalanceCommandCompleter(this));

        Objects.requireNonNull(getCommand("buy")).setExecutor(new BuyCommand(this));
        Objects.requireNonNull(getCommand("buy")).setTabCompleter(new BuyOrSellCommandCompleter(this));

        Objects.requireNonNull(getCommand("sell")).setExecutor(new SellCommand(this));
        Objects.requireNonNull(getCommand("sell")).setTabCompleter(new BuyOrSellCommandCompleter(this));

        Objects.requireNonNull(getCommand("price")).setExecutor(new PriceCommand(this));
        Objects.requireNonNull(getCommand("price")).setTabCompleter(new PriceCommandCompleter(this));


        Objects.requireNonNull(getCommand("assets")).setExecutor(new AssetsCommand(this));
        Objects.requireNonNull(getCommand("liabilities")).setExecutor(new LiabilitiesCommand(this));
        Objects.requireNonNull(getCommand("cards")).setExecutor(new CardsCommand(this));
        Objects.requireNonNull(getCommand("forwards")).setExecutor(new ForwardsCommand(this));
        Objects.requireNonNull(getCommand("futures")).setExecutor(new FuturesCommand(this));
        Objects.requireNonNull(getCommand("networth")).setExecutor(new NetWorthCommand(this));
        Objects.requireNonNull(getCommand("stocks")).setExecutor(new StocksCommand(this));
        Objects.requireNonNull(getCommand("options")).setExecutor(new OptionsCommand(this));

        Objects.requireNonNull(getCommand("pay")).setExecutor(new PayCommand(this));
        Objects.requireNonNull(getCommand("pay")).setTabCompleter(new PayCommandCompleter(this));

        Objects.requireNonNull(getCommand("information")).setExecutor(new InformationCommand(this));
        Objects.requireNonNull(getCommand("list")).setExecutor(new ListCommand(this));
        Objects.requireNonNull(getCommand("networthleaderboard")).setExecutor(new NetWorthLeaderboardCommand(this));

        Objects.requireNonNull(getCommand("premium")).setExecutor(new PremiumCommand(this));
    }

    private void registerEventListeners() {
        new AssetTransferHandler(this);

        new PlayerProfileUpdater(this);
        new PlayerPreviousLocationSetter(this);
        new PlayerRespawnHandler(this);

        new PropertyProtector(this);
    }

    private void scheduleRepeatingTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerAutoSaver(this), 20, 60 * 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new NoteExpirationHandler(this), 30, 15 * 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new DirectMessageProcessor(this), 10,10);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PremiumExpirationHandler(this), 20, 10 * 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MarketTicker(this), 20, 5);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new OfficeTermKeeper(this), 0, 60 * 60 * 20);
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
