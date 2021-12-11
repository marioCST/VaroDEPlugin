package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class FeedCommand extends Command {
    private final VaroDE plugin;

    public FeedCommand(VaroDE plugin) {
        super("feed", "Sättigt  dich", "feed");
        this.setPermission("mario.feed");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mario.heal") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                if (args.length == 0) {
                    player.getFoodData().setLevel(player.getFoodData().getMaxLevel());
                    player.sendMessage(VaroDE.getPrefix() + "Du wurdest gesättigt!");
                }
                else if (args.length == 1) {
                    Player t = player.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                    if (t != null) {
                        t.getFoodData().setLevel(t.getFoodData().getMaxLevel());
                        t.sendMessage(VaroDE.getPrefix() + "Du wurdest gesättigt!");
                        player.sendMessage(VaroDE.getPrefix() + "Der Spieler " + t.getName() + " wurde gesättigt!");
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                else {
                    player.sendMessage(VaroDE.getPrefix() + "/heal [Spieler]");
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
                    t.getFoodData().setLevel(t.getFoodData().getMaxLevel());
                    t.sendMessage(VaroDE.getPrefix() + "Du wurdest gesättigt!");
                    sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + t.getName() + " wurde gesättigt!");
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                }
            }
            else {
                sender.sendMessage(VaroDE.getPrefix() + "/heal [Spieler]");
            }
        }

        return false;
    }
}
