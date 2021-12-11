package de.mariocst.varode.commands.player.movement;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import cn.nukkit.potion.Effect;
import de.mariocst.varode.VaroDE;

public class SpeedCommand extends Command {
    private final VaroDE plugin;

    public SpeedCommand(VaroDE plugin) {
        super("speed", "Gibt dir Speed.", "speed");
        this.setPermission("mario.speed");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.speed") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                try {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("reset")) {
                            player.removeEffect(Effect.SPEED);
                            sender.sendMessage(VaroDE.getPrefix() + "Dein Speed wurde zurückgesetzt!");
                        }
                        else {
                            try {
                                int speed = Integer.parseInt(args[0]);

                                if (speed != 0) {
                                    player.addEffect(
                                            Effect.getEffect(Effect.SPEED)
                                                    .setAmplifier(speed)
                                                    .setDuration(Integer.MAX_VALUE)
                                                    .setVisible(false)
                                    );
                                    sender.sendMessage(VaroDE.getPrefix() + "Dein Speed ist nun: " + speed);
                                }
                                else {
                                    player.removeEffect(Effect.SPEED);
                                    sender.sendMessage(VaroDE.getPrefix() + "Dein Speed wurde zurückgesetzt!");
                                }
                            }
                            catch (NumberFormatException e) {
                                sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value>");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            }
                        }
                    }
                    else if (args.length == 2) {
                        Player t = player.getLevel().getServer().getPlayer(args[1].replaceAll("_", " ").replaceAll("\"", ""));

                        try {
                            if (t != null) {
                                if (args[0].equalsIgnoreCase("reset")) {
                                    t.removeEffect(Effect.SPEED);
                                    sender.sendMessage(VaroDE.getPrefix() + "Der Speed des Spielers " + t.getName() + " wurde zurückgesetzt!");
                                    t.sendMessage(VaroDE.getPrefix() + "Dein Speed wurde zurückgesetzt!");
                                }
                                try {
                                    int speed = Integer.parseInt(args[0]);

                                    if (speed != 0) {
                                        t.addEffect(
                                                Effect.getEffect(Effect.SPEED)
                                                        .setAmplifier(speed)
                                                        .setDuration(Integer.MAX_VALUE)
                                                        .setVisible(false)
                                        );
                                        sender.sendMessage(VaroDE.getPrefix() + "Der Speed des Spielers " + t.getName() + " ist nun: " + speed);
                                        t.sendMessage(VaroDE.getPrefix() + "Dein Speed ist nun: " + speed);
                                    }
                                    else {
                                        t.removeEffect(Effect.SPEED);
                                        sender.sendMessage(VaroDE.getPrefix() + "Der Speed des Spielers " + t.getName() + " wurde zurückgesetzt!");
                                        t.sendMessage(VaroDE.getPrefix() + "Dein Speed wurde zurückgesetzt!");
                                    }
                                }
                                catch (NumberFormatException e) {
                                    sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value> <Spieler>");
                                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                }
                            }
                            else {
                                sender.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            }
                        }
                        catch (NullPointerException e) {
                            sender.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    else {
                        sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value> [Spieler]!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value> [Spieler]!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            try {
                if (args.length == 2) {
                    Player t = VaroDE.getInstance().getServer().getPlayer(args[1].replaceAll("_", " ").replaceAll("\"", ""));

                    try {
                        if (t != null) {
                            if (args[0].equalsIgnoreCase("reset")) {
                                t.removeEffect(Effect.SPEED);
                                sender.sendMessage(VaroDE.getPrefix() + "Der Speed des Spielers " + t.getName() + " wurde zurückgesetzt!");
                                t.sendMessage(VaroDE.getPrefix() + "Dein Speed wurde zurückgesetzt!");
                            }
                            try {
                                int speed = Integer.parseInt(args[0]);

                                if (speed != 0) {
                                    t.addEffect(
                                            Effect.getEffect(Effect.SPEED)
                                                    .setAmplifier(speed)
                                                    .setDuration(Integer.MAX_VALUE)
                                                    .setVisible(false)
                                    );
                                    sender.sendMessage(VaroDE.getPrefix() + "Der Speed des Spielers " + t.getName() + " ist nun: " + speed);
                                    t.sendMessage(VaroDE.getPrefix() + "Dein Speed ist nun: " + speed);
                                }
                                else {
                                    t.removeEffect(Effect.SPEED);
                                    sender.sendMessage(VaroDE.getPrefix() + "Der Speed des Spielers " + t.getName() + " wurde zurückgesetzt!");
                                    t.sendMessage(VaroDE.getPrefix() + "Dein Speed wurde zurückgesetzt!");
                                }
                            }
                            catch (NumberFormatException e) {
                                e.printStackTrace();
                                sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value> <Spieler>");
                            }
                        }
                        else {
                            sender.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                        }
                    }
                    catch (NullPointerException e) {
                        sender.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                    }
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value> <Spieler>!");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(VaroDE.getPrefix() + "/speed <reset|value> <Spieler>!");
            }
        }
        return false;
    }
}
