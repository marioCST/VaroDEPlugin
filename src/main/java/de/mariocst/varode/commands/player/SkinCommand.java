package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class SkinCommand extends Command {
    private final VaroDE plugin;

    public SkinCommand(VaroDE plugin) {
        super("skin", "Übernehme den Skin eines anderen Spielers!", "skin");
        this.setPermission("mario.skin");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.skin") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
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
                                if (t.getName().equals(player.getName())) {
                                    sender.sendMessage(VaroDE.getPrefix() + "Du musst deinen eigenen Skin manuell wieder rein machen!");
                                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                }
                                else {
                                    player.setSkin(t.getSkin());
                                    sender.sendMessage(VaroDE.getPrefix() + "Du hast nun den Skin von: " + t.getName());
                                }
                            }
                        }
                        catch (NullPointerException e) {
                            sender.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "/skin <Spieler>");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    player.sendMessage(VaroDE.getPrefix() + "/skin <Spieler>");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            sender.sendMessage(VaroDE.getPrefix() + "Bitte führe den Command InGame aus!");
        }
        return false;
    }
}
