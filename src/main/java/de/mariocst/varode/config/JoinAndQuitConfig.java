package de.mariocst.varode.config;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.varode.VaroDE;

public class JoinAndQuitConfig {
    private static JoinAndQuitConfig joinAndQuitConfig;

    private final ConfigSection config;

    private final boolean isEmpty;

    private String joinMessage, quitMessage;

    public JoinAndQuitConfig(ConfigSection configSection) {
        joinAndQuitConfig = this;
        this.config = configSection;
        this.isEmpty = this.config.isEmpty();
        this.init();
    }

    public String getJoinMessage() {
        return this.joinMessage;
    }

    public String getQuitMessage() {
        return this.quitMessage;
    }

    public static JoinAndQuitConfig getJoinAndQuitConfig() {
        return joinAndQuitConfig;
    }

    private void init() {
        if (!this.isEmpty) {
            if (this.config.containsKey("join")) {
                this.joinMessage = this.config.getString("join");
            }
            else {
                this.joinMessage = "§e%player% joined the game!";
            }

            if (this.config.containsKey("quit")) {
                this.quitMessage = this.config.getString("quit");
            }
            else {
                this.quitMessage = "§e%player% left the game!";
            }
        } else {
            this.spawnDefaultConfig();
        }
    }

    private void spawnDefaultConfig() {
        this.joinMessage = "§e%player% joined the game!";
        this.quitMessage = "§e%player% left the game!";
        this.save();
    }

    public void save() {
        try {
            this.config.put("join", this.joinMessage);
            this.config.put("quit", this.quitMessage);
            Config c = new Config(VaroDE.getInstance().getDataFolder() + "/joinAndQuit.yml", Config.YAML);
            c.setAll(this.config);
            c.save();
        } catch (NullPointerException e) {
            this.spawnDefaultConfig();
            this.save();
        }
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }
}
