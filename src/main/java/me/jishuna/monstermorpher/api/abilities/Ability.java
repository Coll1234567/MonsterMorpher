package me.jishuna.monstermorpher.api.abilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.commonlib.utils.StringUtils;
import me.jishuna.monstermorpher.api.event.EventWrapper;
import me.jishuna.monstermorpher.api.player.MorphPlayer;
import net.md_5.bungee.api.ChatColor;

public class Ability {

	private final String key;
	private long cooldown;
	private String name;
	private List<String> description;

	public Ability(String key, YamlConfiguration abilityConfig) {
		this.key = key;

		if (abilityConfig != null) {
			loadData(abilityConfig);
		}
	}

	private void loadData(YamlConfiguration abilityConfig) {
		this.name = ChatColor.translateAlternateColorCodes('&', abilityConfig.getString("name", ""));

		String description = ChatColor.translateAlternateColorCodes('&', abilityConfig.getString("description", ""));
		this.cooldown = abilityConfig.getLong("cooldown", 20);

		for (String configKey : abilityConfig.getKeys(false)) {
			description = description.replace("%" + configKey + "%", abilityConfig.getString(configKey));
		}

		List<String> desc = new ArrayList<>();

		for (String line : description.split("\\\\n")) {
			desc.addAll(StringUtils.splitString(line, 30));
		}
		this.description = desc;
	}

	private final Multimap<Class<? extends Event>, EventWrapper<? extends Event>> handlerMap = ArrayListMultimap
			.create();

	public String getKey() {
		return key;
	}

	public long getCooldown() {
		return cooldown;
	}

	public String getName() {
		return name;
	}

	public List<String> getDescription() {
		return description;
	}

	public <T extends Event> void addEventHandler(Class<T> type, BiConsumer<T, MorphPlayer> consumer) {
		this.handlerMap.put(type, new EventWrapper<>(type, consumer));
	}

	public <T extends Event> Collection<EventWrapper<? extends Event>> getEventHandlers(Class<T> type) {
		return this.handlerMap.get(type);
	}

	public boolean checkCooldown(MorphPlayer player) {
		long cooldown = player.getCooldown(this) - System.currentTimeMillis();
		
		if (this.cooldown > 20 && cooldown > 0) {
			player.getPlayer().sendMessage("Cooldown");
		}

		return cooldown <= 0;
	}
	
	protected static YamlConfiguration loadConfig(Plugin owner, String key) {
		Optional<YamlConfiguration> optional = FileUtils.loadResource(owner, "abilities/" + key + ".yml");

		return optional.isPresent() ? optional.get() : null;
	}

}
