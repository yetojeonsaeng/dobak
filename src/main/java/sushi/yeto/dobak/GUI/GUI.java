package sushi.yeto.dobak.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class GUI {
	
	private static Map<Player,GUI> guiMap = new HashMap<Player,GUI>();
	public static GUI getGUI(Player p) { return guiMap.getOrDefault(p, null); }
	
	private Inventory inv;
	private Map<Integer,String> slotMap;
	
	GUI(Player p,String name,int size) {
		inv = Bukkit.createInventory(null,size,name);
		slotMap = new HashMap<Integer,String>();
		init();
		p.openInventory(inv);
		guiMap.put(p, this);
	}
	
	abstract void init();
	public abstract void onClick(InventoryClickEvent e);

	public void setItem(String name, List<String> lore, Object item, int amount, int slot, String value, boolean glow) {
		ItemStack itemStack;

		if (item instanceof Material) {
			itemStack = new ItemStack((Material) item, amount);
		} else if (item instanceof ItemStack) {
			itemStack = (ItemStack) item;
		} else {
			throw new IllegalArgumentException("Unsupported item type");
		}

		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		if (lore != null) meta.setLore(lore);
		if (glow) {
			meta.addEnchant(Enchantment.LURE, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		itemStack.setItemMeta(meta);
		slotMap.put(slot, value);
		inv.setItem(slot, itemStack);
	}

	protected void setItem(String name, List<String> lore, Material material, int amount, int slot, String value, boolean glow) {
		setItem(name, lore, new ItemStack(material, amount), amount, slot, value, glow);
	}



	protected String getValue(int slot) { return slotMap.getOrDefault(slot, null); }
	public void closeGUI(InventoryCloseEvent e) {
		slotMap = null;
		guiMap.remove((Player) e.getPlayer()); 
	}

}
