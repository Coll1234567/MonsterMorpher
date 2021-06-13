package me.jishuna.monstermorpher.morphs;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import me.jishuna.monstermorpher.api.abilities.AbilityManager;
import me.jishuna.monstermorpher.api.morph.Morph;

public class TestMorph extends Morph {
	private static final String KEY = "test";

	public TestMorph(Plugin owner, AbilityManager manager) {
		super(KEY, loadConfig(owner, KEY), manager);
		
		setMorphType(EntityType.SKELETON);
	}

}
