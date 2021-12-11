package de.mariocst.varode.commands.server;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class ConfigurationCommand extends Command {
    private final VaroDE plugin;

    public ConfigurationCommand(VaroDE plugin) {
        super("configuration", "Speicher oder lade die Config neu", "configuration", new String[]{"config"});
        this.setPermission("mario.configuration");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.configuration") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                try {
                    if (args.length == 1) {
                        switch (args[0].toLowerCase()) {
                            case "save": {
                                VaroDE.getInstance().saveConfigs();
                                player.sendMessage(VaroDE.getPrefix() + "Configs gespeichert!");
                                break;
                            }
                            case "reload": {
                                VaroDE.getInstance().loadConfigs();
                                player.sendMessage(VaroDE.getPrefix() + "Configs neu geladen!");
                                break;
                            }
                            default: {
                                player.sendMessage(VaroDE.getPrefix() + "/config <reload|save>");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                break;
                            }
                        }
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "/config <reload|save>");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    player.sendMessage(VaroDE.getPrefix() + "/config <reload|save>");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            try {
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "save": {
                            VaroDE.getInstance().saveConfigs();
                            sender.sendMessage(VaroDE.getPrefix() + "Configs gespeichert!");
                            break;
                        }
                        case "reload": {
                            VaroDE.getInstance().loadConfigs();
                            sender.sendMessage(VaroDE.getPrefix() + "Configs neu geladen!");
                            break;
                        }
                        default: {
                            sender.sendMessage(VaroDE.getPrefix() + "/config <reload|save>");
                            break;
                        }
                    }
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "/config <reload|save>");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(VaroDE.getPrefix() + "/config <reload|save>");
            }
        }

        return false;
    }
}
