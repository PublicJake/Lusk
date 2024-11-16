package it.jakegblp.lusk.utils;

import ch.njol.skript.Skript;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import static it.jakegblp.lusk.utils.Constants.*;


@SuppressWarnings("unused")
public class EventUtils {

    public static boolean willItemsDrop(BlockBreakEvent event) {
        if (Skript.methodExists(BlockBreakEvent.class, "isDropsItems")) {
            return event.isDropItems();
        } else {
            return !event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).isEmpty();
        }
    }

    public static boolean matchesDropEvent(InventoryClickEvent event, PlayerDropItemEvent dropItemEvent) {
        return event != null && dropsAtAll(event)
                && event.getWhoClicked().getUniqueId().equals(dropItemEvent.getPlayer().getUniqueId());
    }

    public static boolean dropsAtAll(InventoryClickEvent event) {
        if (!dropsFromCursor(event) && event.getCurrentItem() == null)
            return false;
        return DROP_ACTION_DATA.contains(event.getAction());
    }

    public static boolean dropsFromCursor(InventoryClickEvent event) {
        return DROP_ACTION_DATA.indexOf(event.getAction()) > 1;
    }

    public static boolean dropsAllItems(InventoryClickEvent event) {
        return DROP_ACTION_DATA.indexOf(event.getAction()) > 0 && DROP_ACTION_DATA.indexOf(event.getAction()) < 3;
    }

    public static boolean picksUp(InventoryClickEvent event) {
        return PICKUP_ACTION_DATA.contains(event.getAction());
    }
    public static boolean placesDown(InventoryClickEvent event) {
        return PLACE_ACTION_DATA.contains(event.getAction());
    }
}
