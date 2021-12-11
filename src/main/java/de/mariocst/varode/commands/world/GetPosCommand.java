package de.mariocst.varode.commands.world;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class GetPosCommand extends Command {
    private final VaroDE plugin;

    public GetPosCommand(VaroDE plugin) {
        super("getpos", "Welche Position habe ich?", "getpos", new String[]{"gp"});
        this.setPermission("mario.getpos");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.getpos") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                try {
                    if (args.length == 0) {
                        sender.sendMessage(VaroDE.getPrefix() + "Deine Position ist: " +
                                "Level: " + player.getLevel().getName() +
                                " X: " + player.getLocation().getX() +
                                " Y: " + player.getLocation().getY() +
                                " Z: " + player.getLocation().getZ());
                    }
                    else if (args.length == 1) {
                        Player t = player.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                        try {
                            if (t == null) {
                                sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                return true;
                            }
                            else {
                                sender.sendMessage(VaroDE.getPrefix() + "Die Position von " + t.getName() + " ist: " +
                                        "Level: " + t.getLevel().getName() +
                                        " X: " + t.getLocation().getX() +
                                        " Y: " + t.getLocation().getY() +
                                        " Z: " + t.getLocation().getZ());
                            }
                        }
                        catch (NullPointerException e) {
                            sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "/getpos [Spieler]!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    player.sendMessage(VaroDE.getPrefix() + "/getpos [Spieler]!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            try {
                if (args.length == 1) {
                    Player t = VaroDE.getInstance().getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                    try {
                        if (t == null) {
                            sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                            return true;
                        }
                        else {
                            sender.sendMessage(VaroDE.getPrefix() + "Die Position von " + t.getName() + " ist: " +
                                    "Level: " + t.getLevel().getName() +
                                    " X: " + t.getLocation().getX() +
                                    " Y: " + t.getLocation().getY() +
                                    " Z: " + t.getLocation().getZ());
                        }
                    }
                    catch (NullPointerException e) {
                        sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                    }
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "/getpos <Spieler>!");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(VaroDE.getPrefix() + "/getpos <Spieler>!");
            }
        }
        return false;
    }
}
