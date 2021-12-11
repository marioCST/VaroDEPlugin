package de.mariocst.varode.commands.inventory;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class ClearInventoryCommand extends Command {
    private final VaroDE plugin;

    public ClearInventoryCommand(VaroDE plugin) {
        super("clearinventory", "Cleart dein Inventar", "clear", new String[]{"clear", "ci", "cleari"});
        this.setPermission("mario.clearinventory");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.clearinventory") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                if (args.length == 0) {
                    player.sendMessage(VaroDE.getPrefix() + "Dein Inventar wurde geleert!");
                    player.getInventory().clearAll();
                }
                else if (args.length == 1) {
                    Player t = player.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                    if (t != null) {
                        t.sendMessage(VaroDE.getPrefix() + "Dein Inventar wurde geleert!");
                        t.getInventory().clearAll();
                        player.sendMessage(VaroDE.getPrefix() + "Das Inventar von " + t.getName() + " wurde geleert!");
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                else {
                    player.sendMessage(VaroDE.getPrefix() + "/clearinventory [Spieler]!");
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
                    t.sendMessage(VaroDE.getPrefix() + "Dein Inventar wurde geleert!");
                    t.getInventory().clearAll();
                    sender.sendMessage(VaroDE.getPrefix() + "Das Inventar von " + t.getName() + " wurde geleert!");
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                }
            }
            else {
                sender.sendMessage(VaroDE.getPrefix() + "/clearinventory [Spieler]!");
            }
        }
        return false;
    }

}
