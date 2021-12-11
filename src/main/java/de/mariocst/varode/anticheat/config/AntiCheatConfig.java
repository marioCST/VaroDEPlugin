package de.mariocst.varode.anticheat.config;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.varode.VaroDE;

import java.util.HashMap;

public class AntiCheatConfig {
    private static AntiCheatConfig antiCheatConfig;

    private final ConfigSection config;

    private final boolean isEmpty;

    public HashMap<Player, Double> velo = new HashMap<>();

    private String webhook;

    private boolean reachA, reachB, selfHit, blockReach;

    private double maxReach, maxVelo, maxBlockReach;

    public AntiCheatConfig(ConfigSection configSection) {
        antiCheatConfig = this;
        this.config = configSection;
        this.isEmpty = this.config.isEmpty();
        this.init();
    }

    public String getWebhook() {
        return this.webhook;
    }

    public boolean isReachA() {
        return this.reachA;
    }

    public boolean isReachB() {
        return this.reachB;
    }

    public boolean isSelfHit() {
        return this.selfHit;
    }

    public boolean isBlockReach() {
        return blockReach;
    }

    public double getMaxBlockReach() {
        return maxBlockReach;
    }

    public double getMaxReach() {
        return this.maxReach;
    }

    public double getMaxVelo() {
        return maxVelo;
    }

    private void init() {
        if (!this.isEmpty) {
            if (this.config.containsKey("webhook")) {
                this.webhook = this.config.getString("webhook");
            }
            else {
                this.webhook = "Undefined";
            }

            if (this.config.containsKey("reachA")) {
                this.reachA = this.config.getBoolean("reachA");
            }
            else {
                this.reachA = false;
            }

            if (this.config.containsKey("reachB")) {
                this.reachB = this.config.getBoolean("reachB");
            }
            else {
                this.reachB = false;
            }

            if (this.config.containsKey("maxReach")) {
                this.maxReach = this.config.getDouble("maxReach");
            }
            else {
                this.maxReach = 3.5;
            }

            if (this.config.containsKey("selfHit")) {
                this.selfHit = this.config.getBoolean("selfHit");
            }
            else {
                this.selfHit = false;
            }

            if (this.config.containsKey("blockReach")) {
                this.blockReach = this.config.getBoolean("blockReach");
            }
            else {
                this.blockReach = false;
            }

            if (this.config.containsKey("maxBlockReach")) {
                this.maxBlockReach = this.config.getDouble("maxBlockReach");
            }
            else {
                this.maxBlockReach = 5.77;
            }

            if (this.config.containsKey("maxVelo")) {
                this.maxVelo = this.config.getDouble("maxVelo");
            }
            else {
                this.maxVelo = 20.0;
            }
        } else {
            this.spawnDefaultConfig();
        }
    }

    private void spawnDefaultConfig() {
        this.webhook = "Undefined";
        this.reachA = false;
        this.reachB = false;
        this.maxReach = 3.5;
        this.selfHit = false;
        this.blockReach = false;
        this.maxBlockReach = 5.77;
        this.maxVelo = 20.0;
        this.save();
    }

    public void save() {
        try {
            this.config.put("webhook", this.webhook);
            this.config.put("reachA", this.reachA);
            this.config.put("reachB", this.reachB);
            this.config.put("maxReach", this.maxReach);
            this.config.put("selfHit", this.selfHit);
            this.config.put("blockReach", this.blockReach);
            this.config.put("maxBlockReach", this.maxBlockReach);
            this.config.put("maxVelo", this.maxVelo);
            Config c = new Config(VaroDE.getInstance().getDataFolder() + "/antiCheat.yml", Config.YAML);
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
