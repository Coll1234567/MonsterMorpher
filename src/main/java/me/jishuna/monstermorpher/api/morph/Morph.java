package me.jishuna.monstermorpher.api.morph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.commonlib.utils.StringUtils;
import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.abilities.AbilityManager;
import me.jishuna.monstermorpher.api.abilities.ActivationType;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.DisguiseConfig.NotifyBar;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import net.md_5.bungee.api.ChatColor;

public class Morph {

	private final String key;
	private EntityType morphType;
	private long duration;
	private String name;
	private List<String> description;

	private final Multimap<ActivationType, Ability> abilityMap = ArrayListMultimap.create();

	public Morph(String key, YamlConfiguration morphConfig, AbilityManager manager) {
		this.key = key;

		if (morphConfig != null) {
			loadData(morphConfig, manager);
		}
	}

	private void loadData(YamlConfiguration morphConfig, AbilityManager manager) {

		this.name = ChatColor.translateAlternateColorCodes('&', morphConfig.getString("name", ""));
		this.duration = morphConfig.getLong("duration", 600) * 20;

		String description = ChatColor.translateAlternateColorCodes('&', morphConfig.getString("description", ""));

		for (String configKey : morphConfig.getKeys(false)) {
			description = description.replace("%" + configKey + "%", morphConfig.getString(configKey));
		}

		List<String> desc = new ArrayList<>();

		for (String line : description.split("\\\\n")) {
			desc.addAll(StringUtils.splitString(line, 30));
		}
		this.description = desc;

		loadAbilities(morphConfig, manager);
	}

	private void loadAbilities(YamlConfiguration morphConfig, AbilityManager manager) {
		ConfigurationSection section = morphConfig.getConfigurationSection("abilities");
		if (section == null)
			return;

		for (String key : section.getKeys(false)) {
			manager.getAbility(key).ifPresent(ability -> {
				ActivationType type = ActivationType.valueOf(section.getString(key, "RIGHT").toUpperCase());
				this.abilityMap.put(type, ability);
			});
		}
	}

	public void onStart(Player player) {
		MobDisguise disguise = new MobDisguise(DisguiseType.getType(this.morphType));
		disguise.setSelfDisguiseVisible(false);
		disguise.setNotifyBar(NotifyBar.NONE);

		DisguiseAPI.disguiseToAll(player, disguise);
	}

	public void onEnd(Player player) {
		Disguise disguise = DisguiseAPI.getDisguise(player);

		if (disguise != null) {
			disguise.removeDisguise();
		}
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public List<String> getDescription() {
		return description;
	}

	public EntityType getMorphType() {
		return morphType;
	}

	public void setMorphType(EntityType morphType) {
		this.morphType = morphType;
	}

	public long getDuration() {
		return duration;
	}

	public Collection<Ability> getAbilities(ActivationType type) {
		return this.abilityMap.get(type);
	}

	protected static YamlConfiguration loadConfig(Plugin owner, String key) {
		Optional<YamlConfiguration> optional = FileUtils.loadResource(owner, "morphs/" + key + ".yml");

		return optional.isPresent() ? optional.get() : null;
	}

}
