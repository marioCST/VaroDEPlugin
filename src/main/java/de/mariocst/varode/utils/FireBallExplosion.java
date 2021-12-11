package de.mariocst.varode.utils;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockTNT;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityShulkerBox;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityExplosive;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.entity.EntityDamageByBlockEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.math.*;
import cn.nukkit.utils.Hash;
import cn.nukkit.utils.Utils;
import it.unimi.dsi.fastutil.longs.LongArraySet;

import java.util.ArrayList;
import java.util.List;

public class FireBallExplosion extends Explosion {
    private final Level level;
    private final Position source;
    private final double size;
    private final Object what;
    private boolean doesDamage = true;
    private List<Block> affectedBlocks = new ArrayList<>();

    public FireBallExplosion(Position center, double size, Entity what) {
        super(center, size, what);
        this.level = center.getLevel();
        this.source = center;
        this.size = Math.max(size, 0);
        this.what = what;
    }

    @Override
    public boolean explodeA() {
        if (what instanceof EntityExplosive && ((Entity) what).isInsideOfWater()) {
            this.doesDamage = false;
            return true;
        }
        if (this.size < 0.1) return false;
        Vector3 vector = new Vector3(0, 0, 0);
        Vector3 vBlock = new Vector3(0, 0, 0);
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
                        vector.setComponents((double) i / (double) 15 * 2d - 1, (double) j / (double) 15 * 2d - 1, (double) k / (double) 15 * 2d - 1);
                        double len = vector.length();
                        vector.setComponents((vector.x / len) * 0.3d, (vector.y / len) * 0.3d, (vector.z / len) * 0.3d);
                        double pointerX = this.source.x;
                        double pointerY = this.source.y;
                        double pointerZ = this.source.z;
                        for (double blastForce = this.size * (Utils.random.nextInt(700, 1301)) / 1000d; blastForce > 0; blastForce -= 0.22499999999999998) {
                            int x = (int) pointerX;
                            int y = (int) pointerY;
                            int z = (int) pointerZ;
                            vBlock.x = pointerX >= x ? x : x - 1;
                            vBlock.y = pointerY >= y ? y : y - 1;
                            vBlock.z = pointerZ >= z ? z : z - 1;
                            if (vBlock.y < 0 || vBlock.y > 255) {
                                break;
                            }
                            Block block = this.level.getBlock(vBlock);
                            if (block.getId() != 0) {
                                blastForce -= (block.getResistance() / 5 + 0.3d) * 0.3d;
                                if (blastForce > 0) {
                                    if (block.getResistance() < 20) {
                                        if (!this.affectedBlocks.contains(block)) {
                                            this.affectedBlocks.add(block);
                                        }
                                    }
                                }
                            }
                            pointerX += vector.x;
                            pointerY += vector.y;
                            pointerZ += vector.z;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean explodeB() {
        LongArraySet updateBlocks = new LongArraySet();
        double yield = (1d / this.size) * 100d;
        if (this.what instanceof Entity) {
            EntityExplodeEvent ev = new EntityExplodeEvent((Entity) this.what, this.source, this.affectedBlocks, yield);
            this.level.getServer().getPluginManager().callEvent(ev);
            if (ev.isCancelled()) {
                return false;
            } else {
                yield = ev.getYield();
                this.affectedBlocks = ev.getBlockList();
            }
        }
        double explosionSize = this.size * 2d;
        double minX = NukkitMath.floorDouble(this.source.x - explosionSize - 1);
        double maxX = NukkitMath.ceilDouble(this.source.x + explosionSize + 1);
        double minY = NukkitMath.floorDouble(this.source.y - explosionSize - 1);
        double maxY = NukkitMath.ceilDouble(this.source.y + explosionSize + 1);
        double minZ = NukkitMath.floorDouble(this.source.z - explosionSize - 1);
        double maxZ = NukkitMath.ceilDouble(this.source.z + explosionSize + 1);
        AxisAlignedBB explosionBB = new SimpleAxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        Entity[] list = this.level.getNearbyEntities(explosionBB, this.what instanceof Entity ? (Entity) this.what : null);
        for (Entity entity : list) {
            double distance = entity.distance(this.source) / explosionSize;
            if (distance <= 1) {
                Vector3 motion = entity.subtract(this.source).normalize();
                int exposure = 1;
                double impact = (1 - distance) * exposure;
                int damage = this.doesDamage ? (int) (((impact * impact + impact) / 2) * 5 * explosionSize + 1) : 0;
                if (this.what instanceof Entity) {
                    entity.attack(new EntityDamageByEntityEvent((Entity) this.what, entity, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, damage));
                } else if (this.what instanceof Block) {
                    entity.attack(new EntityDamageByBlockEvent((Block) this.what, entity, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damage));
                } else {
                    entity.attack(new EntityDamageEvent(entity, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damage));
                }
                entity.setMotion(motion.multiply(impact));
            }
        }
        ItemBlock air = new ItemBlock(Block.get(0));
        BlockEntity container;
        for (Block block : this.affectedBlocks) {
            if (block.getId() == Block.TNT) {
                ((BlockTNT) block).prime(Utils.rand(10, 30), this.what instanceof Entity ? (Entity) this.what : null);
            } else if (block.getId() == Block.BED_BLOCK && (block.getDamage() & 0x08) == 0x08) {
                this.level.setBlockAt((int) block.x, (int) block.y, (int) block.z, BlockID.AIR);
                continue;
            } else if ((container = block.getLevel().getBlockEntity(block)) instanceof InventoryHolder) {
                if (container instanceof BlockEntityShulkerBox) {
                    this.level.dropItem(block.add(0.5, 0.5, 0.5), block.toItem());
                } else {
                    for (Item drop : ((InventoryHolder) container).getInventory().getContents().values()) {
                        this.level.dropItem(block.add(0.5, 0.5, 0.5), drop);
                    }
                }
                ((InventoryHolder) container).getInventory().clearAll();
            } else if (Math.random() * 100 < yield) {
                for (Item drop : block.getDrops(air)) {
                    this.level.dropItem(block.add(0.5, 0.5, 0.5), drop);
                }
            }
            this.level.setBlockAt((int) block.x, (int) block.y, (int) block.z, BlockID.AIR);
            Vector3 pos = new Vector3(block.x, block.y, block.z);
            for (BlockFace side : BlockFace.values()) {
                Vector3 sideBlock = pos.getSide(side);
                long index = Hash.hashBlock((int) sideBlock.x, (int) sideBlock.y, (int) sideBlock.z);
                if (!this.affectedBlocks.contains(sideBlock) && !updateBlocks.contains(index)) {
                    BlockUpdateEvent ev = new BlockUpdateEvent(this.level.getBlock(sideBlock));
                    this.level.getServer().getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        ev.getBlock().onUpdate(Level.BLOCK_UPDATE_NORMAL);
                    }
                    updateBlocks.add(index);
                }
            }
        }
        this.level.addSound(new Vector3(this.source.x, this.source.y, this.source.z), Sound.RANDOM_EXPLODE);
        return true;
    }
}
