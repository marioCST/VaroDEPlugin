package de.mariocst.varode.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityExplosive;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.ExplosionPrimeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import de.mariocst.varode.utils.FireBallExplosion;

import javax.annotation.Nonnull;

public class EntityFireCharge extends EntityProjectile implements EntityExplosive {
    public static final int NETWORK_ID = 85;

    private boolean canExplode;

    private boolean directionChanged;

    private double force;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.45f;
    }

    @Override
    public float getHeight() {
        return 0.45f;
    }

    @Override
    public float getGravity() {
        return 0.0f;
    }

    @Override
    public float getDrag() {
        return 0.0f;
    }

    @Override
    public double getDamage() {
        return 5;
    }

    public double getForce() {
        return force;
    }

    public void setForce(double force) {
        this.force = force;
    }

    @Nonnull
    @Override
    public String getName() {
        return "FireCharge";
    }

    public EntityFireCharge(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }

    public EntityFireCharge(FullChunk chunk, CompoundTag nbt, double force) {
        this(chunk, nbt, null);
        this.force = force;
    }

    public EntityFireCharge(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        this(chunk, nbt, shootingEntity, false);
    }

    public EntityFireCharge(FullChunk chunk, CompoundTag nbt, Entity shootingEntity, double force) {
        this(chunk, nbt, shootingEntity, false);
        this.force = force;
    }

    public EntityFireCharge(FullChunk chunk, CompoundTag nbt, Entity shootingEntity, boolean critical) {
        super(chunk, nbt, shootingEntity);
    }

    public EntityFireCharge(FullChunk chunk, CompoundTag nbt, Entity shootingEntity, boolean critical, double force) {
        super(chunk, nbt, shootingEntity);
        this.force = force;
    }

    public void setExplode(boolean bool) {
        this.canExplode = bool;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }

        if (this.age > 1200 || this.isCollided) {
            if (this.isCollided && this.canExplode) {
                this.explode();
            }
            this.close();
        }

        return super.onUpdate(currentTick);
    }

    @Override
    public void onCollideWithEntity(Entity entity) {
        this.isCollided = true;
        ExplosionPrimeEvent ev = new ExplosionPrimeEvent(this, force);
        this.server.getPluginManager().callEvent(ev);
        if (!ev.isCancelled()) {
            FireBallExplosion explosion = new FireBallExplosion(this, (float) ev.getForce(), this.shootingEntity);
            if (ev.isBlockBreaking() && this.level.getGameRules().getBoolean(GameRule.MOB_GRIEFING)) {
                explosion.explodeA();
            }
            explosion.explodeB();
        }
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (!directionChanged && source instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) source).getDamager() instanceof Player) {
                directionChanged = true;
                this.setMotion(((EntityDamageByEntityEvent) source).getDamager().getLocation().getDirectionVector());
            }
        }

        return true;
    }

    @Override
    public void explode() {
        ExplosionPrimeEvent ev = new ExplosionPrimeEvent(this, force);
        this.server.getPluginManager().callEvent(ev);
        if (!ev.isCancelled()) {
            FireBallExplosion explosion = new FireBallExplosion(this, (float) ev.getForce(), this.shootingEntity);
            if (ev.isBlockBreaking() && this.level.getGameRules().getBoolean(GameRule.MOB_GRIEFING)) {
                explosion.explodeA();
            }
            explosion.explodeB();
        }
    }

    @Override
    public void onCollideWithPlayer(EntityHuman entityPlayer) {
        this.isCollided = true;
        ExplosionPrimeEvent ev = new ExplosionPrimeEvent(this, force);
        this.server.getPluginManager().callEvent(ev);
        if (!ev.isCancelled()) {
            FireBallExplosion explosion = new FireBallExplosion(this, (float) ev.getForce(), this.shootingEntity);
            if (ev.isBlockBreaking() && this.level.getGameRules().getBoolean(GameRule.MOB_GRIEFING)) {
                explosion.explodeA();
            }
            explosion.explodeB();
        }
    }

    @Override
    public boolean onInteract(Player player, Item item) {
        this.isCollided = true;
        return this.setMotion(player.getDirectionVector());
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        return this.onInteract(player, item);
    }
}
