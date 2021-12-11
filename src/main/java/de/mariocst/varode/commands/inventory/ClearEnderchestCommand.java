package de.mariocst.varode.commands.inventory;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class ClearEnderchestCommand extends Command {
    private final VaroDE plugin;

    public ClearEnderchestCommand(VaroDE plugin) {
        super("clearenderchest", "Cleart deine EnderChest", "clearenderchest", new String[]{"cec", "clearec"});
        this.setPermission("mario.clearenderchest");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.clearenderchest") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                if (args.length == 0) {
                    player.sendMessage(VaroDE.getPrefix() + "Deine Enderchest wurde geleert!");
                    player.getEnderChestInventory().clearAll();
                }
                else if (args.length == 1) {
                    Player t = player.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                    if (t != null) {
                        t.sendMessage(VaroDE.getPrefix() + "Deine Enderchest wurde geleert!");
                        t.getEnderChestInventory().clearAll();
                        player.sendMessage(VaroDE.getPrefix() + "Die Enderchest von " + t.getName() + " wurde geleert!");
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                else {
                    player.sendMessage(VaroDE.getPrefix() + "/clearenderchest [Spieler]!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            if (args.length == 1) {
                Player t = VaroDE.getInstance().getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                if (t != null) {
                    t.sendMessage(VaroDE.getPrefix() + "Deine Enderchest wurde geleert!");
                    t.getEnderChestInventory().clearAll();
                    sender.sendMessage(VaroDE.getPrefix() + "Die Enderchest von " + t.getName() + " wurde geleert!");
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                }
            }
            else {
                sender.sendMessage(VaroDE.getPrefix() + "/clearenderchest [Spieler]!");
            }
        }
        return false;
    }
}
