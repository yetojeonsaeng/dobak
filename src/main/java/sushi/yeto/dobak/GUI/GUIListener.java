package sushi.yeto.dobak.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void guiClick(InventoryClickEvent event) {
        GUI gui = GUI.getGUI((Player) event.getWhoClicked());
        if(gui != null) gui.onClick(event);
    }

    @EventHandler
    public void guiClose(InventoryCloseEvent event) {
        GUI gui = GUI.getGUI((Player) event.getPlayer());
        if(gui != null) gui.closeGUI(event);
    }
}
