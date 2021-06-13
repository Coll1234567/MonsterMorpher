package me.jishuna.monstermorpher;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public enum PluginKeys {
	ABILITY_CRYSTAL("ability_crystal");

	private final String name;
	private NamespacedKey key;

	private PluginKeys(String name) {
		this.name = name;
	}

	public static void initialize(Plugin plugin) {
		for (PluginKeys plguinKey : PluginKeys.values()) {
			plguinKey.key = new NamespacedKey(plugin, plguinKey.name);
		}
	}

	public NamespacedKey getKey() {
		return this.key;
	}

}
