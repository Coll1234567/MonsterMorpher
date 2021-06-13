package me.jishuna.monstermorpher.api.morph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;

import me.jishuna.monstermorpher.MonsterMorpher;
import me.jishuna.monstermorpher.api.event.MorphSetupEvent;
import me.jishuna.monstermorpher.morphs.SkeletonMorph;
import me.jishuna.monstermorpher.morphs.TestMorph;

public class MorphManager {

	private Map<String, Morph> morphs = new HashMap<>();

	private final MonsterMorpher plugin;

	public MorphManager(MonsterMorpher plugin) {
		this.plugin = plugin;
	}

	public void reloadMorphs() {
		this.morphs.clear();

		MorphSetupEvent event = new MorphSetupEvent();
		event.getMorphsToAdd().addAll(this.getDefaultMorphs());
		Bukkit.getPluginManager().callEvent(event);

		event.getMorphsToAdd().forEach(morph -> {
			this.morphs.put(morph.getKey(), morph);
		});
	}

	private List<Morph> getDefaultMorphs() {
		List<Morph> defaultMorphs = new ArrayList<>();
		
		defaultMorphs.add(new TestMorph(this.plugin, this.plugin.getAbilityManager()));
		defaultMorphs.add(new SkeletonMorph(this.plugin, this.plugin.getAbilityManager()));
		
		return defaultMorphs;
	}

	public Optional<Morph> getMorph(String key) {
		return Optional.ofNullable(this.morphs.get(key));
	}

	public Collection<Morph> getAllMorphs() {
		return this.morphs.values();
	}
}
