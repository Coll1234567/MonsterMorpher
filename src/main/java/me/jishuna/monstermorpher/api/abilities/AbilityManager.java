package me.jishuna.monstermorpher.api.abilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;

import me.jishuna.monstermorpher.MonsterMorpher;
import me.jishuna.monstermorpher.abilities.ArrowBarrageAbility;
import me.jishuna.monstermorpher.abilities.BlastoffAbility;
import me.jishuna.monstermorpher.abilities.FlesheaterAbility;
import me.jishuna.monstermorpher.abilities.SonarAbility;
import me.jishuna.monstermorpher.abilities.TestAbility;
import me.jishuna.monstermorpher.abilities.WitherSkullAbility;
import me.jishuna.monstermorpher.api.event.AbilitySetupEvent;

public class AbilityManager {
	private Map<String, Ability> abilities = new HashMap<>();

	private final MonsterMorpher plugin;

	public AbilityManager(MonsterMorpher plugin) {
		this.plugin = plugin;
	}

	public void reloadAbilities() {
		this.abilities.clear();

		AbilitySetupEvent event = new AbilitySetupEvent();
		event.getAbilitiesToAdd().addAll(this.getDefaultAbilities());
		Bukkit.getPluginManager().callEvent(event);

		event.getAbilitiesToAdd().forEach(ability -> {
			this.abilities.put(ability.getKey(), ability);
		});
	}

	private List<Ability> getDefaultAbilities() {
		List<Ability> defaultAbilities = new ArrayList<>();

		defaultAbilities.add(new TestAbility(this.plugin));
		defaultAbilities.add(new ArrowBarrageAbility(this.plugin));
		defaultAbilities.add(new WitherSkullAbility(this.plugin));
		defaultAbilities.add(new BlastoffAbility(this.plugin));
		defaultAbilities.add(new SonarAbility(this.plugin));
		defaultAbilities.add(new FlesheaterAbility(this.plugin));

		return defaultAbilities;
	}

	public Optional<Ability> getAbility(String key) {
		return Optional.ofNullable(this.abilities.get(key));
	}

	public Collection<Ability> getAllAbilities() {
		return this.abilities.values();
	}
}
