package de.mariocst.varode.config;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.varode.VaroDE;

public class JoinSpawnConfig {
    private final ConfigSection config;

    private final boolean isEmpty;

    private String worldName;
    private double x, y, z;
    private double yaw, pitch;

    public JoinSpawnConfig(ConfigSection configSection) {
        this.config = configSection;
        this.isEmpty = this.config.isEmpty();
        this.init();
    }

    public String getWorldName() {
        return this.worldName;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getPitch() {
        return this.pitch;
    }

    private void init() {
        if (!this.isEmpty) {
            if (this.config.containsKey("world_name")) {
                this.worldName = this.config.getString("world_name");
            }
            else {
                this.worldName = "Lobby";
            }

            if (this.config.containsKey("x")) {
                this.x = this.config.getDouble("x");
            }
            else {
                this.x = 0.0;
            }

            if (this.config.containsKey("y")) {
                this.y = this.config.getDouble("y");
            }
            else {
                this.y = 0.0;
            }

            if (this.config.containsKey("z")) {
                this.z = this.config.getDouble("z");
            }

            else {
                this.z = 0.0;
            }

            if (this.config.containsKey("yaw")) {
                this.yaw = this.config.getDouble("yaw");
            }

            else {
                this.yaw = 0.0;
            }

            if (this.config.containsKey("pitch")) {
                this.pitch = this.config.getDouble("pitch");
            }
            else {
                this.pitch = 0.0;
            }
        } else {
            this.spawnDefaultConfig();
        }
    }

    private void spawnDefaultConfig() {
        this.worldName = "Lobby";
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.yaw = 0.0;
        this.pitch = 0.0;
        this.save();
    }

    public void save() {
        try {
            this.config.set("world_name", this.worldName);
            this.config.set("x", this.x);
            this.config.set("y", this.y);
            this.config.set("z", this.z);
            this.config.set("yaw", this.yaw);
            this.config.set("pitch", this.pitch);
            Config c = new Config(VaroDE.getInstance().getDataFolder() + "/joinSpawn.yml", Config.YAML);
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
