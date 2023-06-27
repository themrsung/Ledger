package jbs.ledger.listeners.property;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.address.Headquartered;
import jbs.ledger.listeners.LedgerListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public final class PropertyProtector extends LedgerListener {
    public PropertyProtector(Ledger ledger) {
        super(ledger);
    }

    public static List<EntityType> PROTECTED_ENTITIES = Arrays.asList(
            EntityType.COW,
            EntityType.PIG,
            EntityType.VILLAGER,
            EntityType.ITEM_FRAME,
            EntityType.GLOW_ITEM_FRAME,
            EntityType.PAINTING,
            EntityType.TEXT_DISPLAY,
            EntityType.LEASH_HITCH,
            EntityType.CHICKEN,
            EntityType.SHEEP,
            EntityType.MUSHROOM_COW,
            EntityType.SNOWMAN,
            EntityType.IRON_GOLEM,
            EntityType.LLAMA,
            EntityType.CAT,
            EntityType.WOLF,
            EntityType.GOAT,
            EntityType.BOAT,
            EntityType.ITEM_DISPLAY,
            EntityType.ARMOR_STAND
    );

    public static List<EntityType> DISABLED_ENTITIES = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.HUSK,
            EntityType.DROWNED,
            EntityType.SKELETON,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.WITCH,
            EntityType.PILLAGER,
            EntityType.ENDERMAN
    );



    @Nullable
    private Headquartered getOwner(Location location) {
        for (Assetholder h : getState().getAssetholders()) {
            if (h.getAddress() != null) {
                if (h.getAddress().equals(location)) {
                    return h;
                } else if (h.getProtectionRadius() > 0) {
                    try {

                        double d = location.distance(h.getAddress());
                        if (d <= h.getProtectionRadius()) {
                            return h;
                        }

                    } catch (IllegalArgumentException e) {
                        //
                    }
                }
            }
        }

        return null;
    }

    private boolean hasAccess(@Nullable Person person, Location location) {
        if (person == null) return false;

        Headquartered owner = getOwner(location);
        if (owner == null) return true;

        return owner.hasPropertyAccess(person);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Person person = getState().getPerson(e.getPlayer().getUniqueId());
        Location location = e.getBlock().getLocation();

        if (hasAccess(person, location)) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        Person person = getState().getPerson(e.getPlayer().getUniqueId());
        Location location = e.getBlock().getLocation();

        if (hasAccess(person, location)) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Location location = e.getEntity().getLocation();

        if (getOwner(location) == null) return;
        if (!PROTECTED_ENTITIES.contains(e.getEntityType())) return;

        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            Person person = getState().getPerson(player.getUniqueId());

            if (hasAccess(person, location)) return;
        } else if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player shooter = (Player) projectile.getShooter();
                Person p = getState().getPerson(shooter.getUniqueId());
                if (p != null) {
                    if (hasAccess(p, location)) return;
                }
            }
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent e) {
        Location location = e.getEntity().getLocation();

        if (getOwner(location) == null) return;

        if (!DISABLED_ENTITIES.contains(e.getEntityType())) return;


        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) return;

        Person person = getState().getPerson(e.getPlayer().getUniqueId());
        Location location = e.getClickedBlock().getLocation();
        if (hasAccess(person, location)) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTakeBook(PlayerTakeLecternBookEvent e) {
        Person person = getState().getPerson(e.getPlayer().getUniqueId());
        Location location = e.getLectern().getLocation();

        if (hasAccess(person, location)) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onExplosion(EntityExplodeEvent e) {
        if (getOwner(e.getLocation()) == null) return;

        if (e.getEntityType() == EntityType.CREEPER) {
            e.setCancelled(true);
        }
    }
}
