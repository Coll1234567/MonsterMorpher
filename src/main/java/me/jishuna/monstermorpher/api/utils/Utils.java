package me.jishuna.monstermorpher.api.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.commonlib.items.ItemBuilder;
import me.jishuna.monstermorpher.PluginKeys;

public class Utils {

	private static final ItemStack ABILITY_ITEM = new ItemBuilder(Material.NETHER_STAR).withName("Ability Crystal")
			.withPersistantData(PluginKeys.ABILITY_CRYSTAL.getKey(), PersistentDataType.BYTE, (byte) 1).build();

	public static ItemStack getAbilityItem() {
		return ABILITY_ITEM;
	}

	public static boolean isAbilityItem(ItemStack item) {
		if (item == null || item.getItemMeta() == null)
			return false;

		ItemMeta meta = item.getItemMeta();

		if (meta.getPersistentDataContainer().has(PluginKeys.ABILITY_CRYSTAL.getKey(), PersistentDataType.BYTE))
			return true;

		return false;
	}

}
