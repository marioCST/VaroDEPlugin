package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class GetGamemodeCommand extends Command {
    private final VaroDE plugin;

    public GetGamemodeCommand(VaroDE plugin) {
        super("getgamemode", "Lässt dich wissen, welchen Gamemode ein Spieler hat.", "getgamemode", new String[]{"ggm"});
        this.setPermission("mario.getgamemode");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.getgamemode") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                try {
                    if (args.length == 1) {
                        Player t = player.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                        try {
                            if (t == null) {
                                sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                return true;
                            }
                            else {
                                player.sendMessage(VaroDE.getPrefix() + "Der Gamemode von " + t.getName() + " ist " + t.getGamemode());
                            }
                        }
                        catch (NullPointerException e) {
                            sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            return true;
                        }
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "/getgamemode <Spieler>!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    player.sendMessage(VaroDE.getPrefix() + "/getgamemode <Spieler>!");
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
                            sender.sendMessage(VaroDE.getPrefix() + "Der Gamemode von " + t.getName() + " ist " + t.getGamemode());
                        }
                    }
                    catch (NullPointerException e) {
                        sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                        return true;
                    }
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "/getgamemode <Spieler>!");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(VaroDE.getPrefix() + "/getgamemode <Spieler>!");
            }
        }
        return false;
    }
}
