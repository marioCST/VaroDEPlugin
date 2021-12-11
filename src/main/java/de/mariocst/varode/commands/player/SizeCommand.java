package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class SizeCommand extends Command {
    private final VaroDE plugin;

    public SizeCommand(VaroDE plugin) {
        super("size", "Lässt dich größer oder kleiner werden", "size", new String[]{"grösse", "scale", "sz"});
        this.setPermission("mario.size");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.size") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (args.length == 0) {
                    VaroDE.getInstance().getSizeForm().openSize(player);
                }
                else if (args.length == 1) {
                    if (player.hasPermission("mario.size.custom") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                        try {
                            float getSize = Float.parseFloat(args[0]);
                            if (getSize >= 72) {
                                sender.sendMessage(VaroDE.getPrefix() + "Bitte wähle eine kleinere Größe! Ab 72 laggt Minecraft hart ^^");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            } else if (getSize <= -72) {
                                sender.sendMessage(VaroDE.getPrefix() + "Bitte wähle eine größere Größe! Ab -72 laggt Minecraft hart ^^");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            } else {
                                if (args[0].equals("1")) {
                                    sender.sendMessage(VaroDE.getPrefix() + "Deine Größe wurde zurückgesetzt!");
                                    player.setScale(1);
                                } else {
                                    float size = Float.parseFloat(args[0]);
                                    sender.sendMessage(VaroDE.getPrefix() + "Deine Größe wurde auf " + args[0] + " gesetzt!");
                                    player.setScale(size);
                                }
                            }
                        } catch (NumberFormatException e) {
                            sender.sendMessage(VaroDE.getPrefix() + "Bitte gib eine (gültige) Zahl ein!");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "Keine Rechte für eine custom Größe!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                } else {
                    sender.sendMessage(VaroDE.getPrefix() + "Bitte schreibe eine einspaltige Größe!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            if (args.length == 2) {
                Player t = VaroDE.getInstance().getServer().getPlayer(args[1].replaceAll("_", " ").replaceAll("\"", ""));

                try {
                    if (t.getName() != null) {
                        try {
                            t.setScale(Float.parseFloat(args[0]));
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(VaroDE.getPrefix() + "Bitte gib eine gültige Zahl ein!");
                        }
                    }
                    else {
                        sender.sendMessage(VaroDE.getPrefix() + "Dieser Spieler existiert nicht!");
                    }
                }
                catch (NullPointerException e) {
                    sender.sendMessage(VaroDE.getPrefix() + "Dieser Spieler existiert nicht!");
                }
            }
            else {
                sender.sendMessage(VaroDE.getPrefix() + "/size <Größe> <Spieler>!");
            }
        }
        return false;
    }
}
