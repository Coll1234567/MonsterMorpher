package me.jishuna.monstermorpher.morphs;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import me.jishuna.monstermorpher.api.abilities.AbilityManager;
import me.jishuna.monstermorpher.api.morph.Morph;

public class MobMorph extends Morph {

	public MobMorph(Plugin owner, String key, EntityType type, AbilityManager manager) {
		super(key, loadConfig(owner, key), manager);
		
		setMorphType(type);
	}

}
