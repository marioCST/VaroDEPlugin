package de.mariocst.varode;

import cn.nukkit.AdventureSettings;
import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.CommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import de.mariocst.varode.anticheat.checks.combat.ReachA;
import de.mariocst.varode.anticheat.checks.combat.ReachB;
import de.mariocst.varode.anticheat.checks.combat.SelfHit;
import de.mariocst.varode.anticheat.checks.world.BlockReach;
import de.mariocst.varode.anticheat.config.AntiCheatConfig;
import de.mariocst.varode.config.*;
import de.mariocst.varode.commands.chat.*;
import de.mariocst.varode.commands.inventory.*;
import de.mariocst.varode.commands.others.*;
import de.mariocst.varode.commands.player.*;
import de.mariocst.varode.commands.player.movement.*;
import de.mariocst.varode.commands.server.*;
import de.mariocst.varode.commands.setter.*;
import de.mariocst.varode.commands.world.*;
import de.mariocst.varode.forms.FormListener;
import de.mariocst.varode.forms.ReportForm;
import de.mariocst.varode.forms.SizeForm;
import de.mariocst.varode.listeners.*;
import de.mariocst.varode.webhook.DiscordWebhook;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;

public class VaroDE extends PluginBase {
    private static VaroDE instance;

    private static String prefix;

    private static AntiCheatConfig antiCheatConfig;

    private static MasterConfig masterConfig;
    private static JoinAndQuitConfig joinAndQuitConfig;
    private static JoinSpawnConfig joinSpawnConfig;
    private static VaroLastPosConfig varoLastPosConfig;

    @Getter
    private SizeForm sizeForm;

    @Getter
    private ReportForm reportForm;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.loadConfigs();

        setPrefix(masterConfig.getPrefix());

        this.register();

        if (masterConfig.getWebhookLink().equals("Undefined")) this.critical("Kein Webhook Link angegeben!");

        this.log("VaroDE Plugin geladen!");
    }

    @Override
    public void onDisable() {
        this.saveConfigs();

        this.log("VaroDE Plugin entladen!");
    }

    public void log(String text) {
        this.getLogger().info(getPrefix() + text);
    }

    public void critical(String text) {
        this.getLogger().critical(getPrefix() + text);
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        VaroDE.prefix = prefix;
    }

    public void loadConfigs() {
        Config c = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
        masterConfig = new MasterConfig(c.getRootSection());

        Config aCC = new Config(this.getDataFolder() + "/antiCheat.yml", Config.YAML);
        antiCheatConfig = new AntiCheatConfig(aCC.getRootSection());

        Config jAQ = new Config(this.getDataFolder() + "/joinAndQuit.yml", Config.YAML);
        joinAndQuitConfig = new JoinAndQuitConfig(jAQ.getRootSection());

        Config jSC = new Config(this.getDataFolder() + "/joinSpawn.yml", Config.YAML);
        joinSpawnConfig = new JoinSpawnConfig(jSC.getRootSection());

        Config vLP = new Config(this.getDataFolder() + "/varoLastPos.yml", Config.YAML);
        varoLastPosConfig = new VaroLastPosConfig(vLP.getRootSection());
    }

    public void saveConfigs() {
        varoLastPosConfig.save();
        joinSpawnConfig.save();
        joinAndQuitConfig.save();
        antiCheatConfig.save();
        masterConfig.save();
    }

    private void register() {
        CommandMap commandMap = this.getServer().getCommandMap();

        // Chat
        commandMap.register("broadcast", new BroadcastCommand(this));
        commandMap.register("chatclear", new ChatClearCommand(this));

        //Inventory
        commandMap.register("clearenderchest", new ClearEnderchestCommand(this));
        commandMap.register("clearinventory", new ClearInventoryCommand(this));
        commandMap.register("id", new IDCommand(this));
        if (this.getServer().getPluginManager().getPlugin("FakeInventories") != null) {
            commandMap.register("invsee", new InvseeCommand(this));
        }
        else {
            this.critical("Plugin \"FakeInventories\" wurde nicht gefunden! Invsee wird deaktiviert! Download: https://ci.opencollab.dev//job/NukkitX/job/FakeInventories/job/master/");
        }

        // Others
        commandMap.register("discord", new DiscordCommand(this));

        // Player
        commandMap.register("feed", new FeedCommand(this));
        commandMap.register("getgamemode", new GetGamemodeCommand(this));
        commandMap.register("heal", new HealCommand(this));
        commandMap.register("near", new NearCommand(this));
        commandMap.register("nick", new NickCommand(this));
        commandMap.register("nightvision", new NightvisionCommand(this));
        commandMap.register("realname", new RealnameCommand(this));
        commandMap.register("size", new SizeCommand(this));
        commandMap.register("skin", new SkinCommand(this));
        commandMap.register("unnick", new UnnickCommand(this));
            // Movement
            commandMap.register("climb", new ClimbCommand(this));
            commandMap.register("fly", new FlyCommand(this));
            commandMap.register("speed", new SpeedCommand(this));

        // Server
        commandMap.register("configuration", new ConfigurationCommand(this));
        commandMap.register("kickall", new KickAllCommand(this));
        commandMap.register("onlineplayers", new OnlinePlayersCommand(this));
        commandMap.register("report", new ReportCommand(this));

        // Setter
        commandMap.register("setlink", new SetLinkCommand(this));
        commandMap.register("setprefix", new SetPrefixCommand(this));

        // World
        commandMap.register("day", new DayCommand(this));
        commandMap.register("getpos", new GetPosCommand(this));
        commandMap.register("lobby", new LobbyCommand(this));
        commandMap.register("night", new NightCommand(this));
        commandMap.register("varo", new VaroCommand(this));


        // Listener
        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new AchievementListener(), this);
        manager.registerEvents(new BedrockListener(), this);
        manager.registerEvents(new BFFAListener(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new FireChargeListener(), this);
        manager.registerEvents(new FormListener(), this);
        manager.registerEvents(new JoinAndQuitListener(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new JumpPadListener(), this);
        manager.registerEvents(new PerWorldChatListener(), this);
        manager.registerEvents(new VaroPositionListener(), this);


        // AntiCheat Checks
        manager.registerEvents(new BlockReach(), this);
        manager.registerEvents(new ReachA(), this);
        manager.registerEvents(new ReachB(), this);
        manager.registerEvents(new SelfHit(), this);


        // Forms
        this.reportForm = new ReportForm();
        this.sizeForm = new SizeForm();
    }

    public static VaroDE getInstance() {
        return instance;
    }

    public MasterConfig getMasterConfig() {
        return masterConfig;
    }

    public static JoinSpawnConfig getJoinSpawnConfig() {
        return joinSpawnConfig;
    }

    public static AntiCheatConfig getAntiCheatConfig() {
        return antiCheatConfig;
    }

    public static boolean hasFly(Player player) {
        return player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT);
    }

    public void sendReport(Player player, Player reported, String reason) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook(masterConfig.getWebhookLink());

        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Report")
                .setDescription("Ein Spieler hat jemanden reported!")
                .addField("Spieler", player.getName(), false)
                .addField("Reported", reported.getName(), false)
                .addField("Grund", reason, false)
                .setColor(Color.RED));

        try {
            webhook.execute();
        }
        catch (IOException e) {
            this.getLogger().error(e.getLocalizedMessage());
        }
    }

    public void sendReport(Player player, IPlayer reported, String reason) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook(masterConfig.getWebhookLink());

        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Report")
                .setDescription("Ein Spieler hat jemanden reported!")
                .addField("Spieler", player.getName(), false)
                .addField("Reported", reported.getName(), false)
                .addField("Grund", reason, false)
                .setColor(Color.RED));

        try {
            webhook.execute();
        }
        catch (IOException e) {
            this.getLogger().error(e.getLocalizedMessage());
        }
    }

    public void flag(String check, String details, Player flagged) {
        this.critical("Der Spieler " + flagged.getName() + " wurde für " + check + " geflaggt! Details: " + details);

        double velo = 0.0;

        if (antiCheatConfig.velo.containsKey(flagged)) {
            velo = antiCheatConfig.velo.get(flagged);
            antiCheatConfig.velo.remove(flagged);
        }

        velo += 1.0;

        antiCheatConfig.velo.put(flagged, velo);

        for (Player player : this.getServer().getOnlinePlayers().values()) {
            if (player.hasPermission("mario.staff") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                player.sendMessage(getPrefix() + "Der Spieler " + flagged.getName() + " wurde für " + check + " geflaggt! Details: " + details);
            }
        }

        if (velo >= antiCheatConfig.getMaxVelo()) {
            antiCheatConfig.velo.remove(flagged);
            flagged.kick("§cDu wurdest gekickt, weil du gecheatet hast! Bitte deaktiviere deine Cheats, während du auf dem Server spielst.", false);

            for (Player player : this.getServer().getOnlinePlayers().values()) {
                if (player.hasPermission("mario.staff") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                    player.sendMessage(getPrefix() + "Der Spieler " + flagged.getName() + " wurde für's Cheaten gekickt!");
                }
            }
        }

        if (antiCheatConfig.getWebhook().equals("Undefined")) return;

        DiscordWebhook webhook = new DiscordWebhook(antiCheatConfig.getWebhook());

        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("AntiCheat")
                .setDescription("Ein Spieler wurde geflagged!")
                .addField("Spieler", flagged.getName(), false)
                .addField("Check", check, false)
                .addField("Details", details, false)
                .setColor(Color.RED));

        try {
            webhook.execute();
        }
        catch (IOException e) {
            this.getLogger().error(e.getLocalizedMessage());
        }

        if (velo >= antiCheatConfig.getMaxVelo()) {
            DiscordWebhook webhook2 = new DiscordWebhook(antiCheatConfig.getWebhook());

            webhook2.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("AntiCheat")
                    .setDescription("Ein Spieler wurde gekickt!")
                    .addField("Spieler", flagged.getName(), false)
                    .setColor(Color.RED));

            try {
                webhook2.execute();
            }
            catch (IOException e) {
                this.getLogger().error(e.getLocalizedMessage());
            }
        }
    }
}
