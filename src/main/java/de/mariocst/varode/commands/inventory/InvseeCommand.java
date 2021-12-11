package de.mariocst.varode.commands.inventory;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import com.nukkitx.fakeinventories.inventory.ChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.DoubleChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent;
import de.mariocst.varode.VaroDE;

public class InvseeCommand extends Command {
    private final VaroDE plugin;

    public InvseeCommand(VaroDE plugin) {
        super("invsee", "Öffnet das Inventar eines Anderen Spielers", "invsee");
        this.setPermission("mario.invsee");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.invsee") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) {
                try {
                    if (args.length == 2) {
                        Player t = player.getLevel().getServer().getPlayer(args[1].replaceAll("_", " ").replaceAll("\"", ""));

                        try {
                            if (t == null) {
                                player.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler!");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                return true;
                            }
                            else {
                                if (t == player) {
                                    player.sendMessage(VaroDE.getPrefix() + "Du kannst diesen Command nicht für dein eigenes Inventar nutzen");
                                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                    return true;
                                }
                                else {
                                    DoubleChestFakeInventory inv = new DoubleChestFakeInventory();
                                    ChestFakeInventory echest = new ChestFakeInventory();
                                    inv.addListener(this::onSlotChange);
                                    echest.addListener(this::onSlotChange);

                                    if (args[0].equalsIgnoreCase("echest")) {
                                        echest.setContents(t.getEnderChestInventory().getContents());
                                        echest.setName(t.getName() + "'s Ender Chest");
                                        player.addWindow(echest);
                                        return true;
                                    }
                                    else if (args[0].equalsIgnoreCase("inv")){
                                        inv.setContents(t.getInventory().getContents());
                                        inv.setName(t.getName() + "'s Inventar");
                                        player.addWindow(inv);
                                        return true;
                                    }
                                    else {
                                        player.sendMessage(VaroDE.getPrefix() + "/invsee <inv|echest> <Spieler>");
                                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                    }
                                }
                            }
                        }
                        catch (NullPointerException e) {
                            player.sendMessage(VaroDE.getPrefix() + "Unbekannter Spieler");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    else {
                        player.sendMessage(VaroDE.getPrefix() + "/invsee <inv/echest> <Spieler>");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    player.sendMessage(VaroDE.getPrefix() + "/invsee <inv/echest> <Spieler>");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            sender.sendMessage(VaroDE.getPrefix() + "Bitte führe den Befehl InGame aus!");
        }
        return false;
    }

    private void onSlotChange(FakeSlotChangeEvent e) {
        if (e.getInventory() instanceof DoubleChestFakeInventory) {
            if (e.getInventory().getName().contains("'s Ender Chest") || e.getInventory().getName().contains("'s Inventar")) {
                e.setCancelled(true);
            }
        }
    }
}
