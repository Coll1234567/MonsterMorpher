package me.jishuna.monstermorpher.abilities;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.player.MorphPlayer;

public class FlesheaterAbility extends Ability {
	private static final String KEY = "flesheater";

	public FlesheaterAbility(Plugin owner) {
		super(KEY, loadConfig(owner, KEY));

		addEventHandler(EntityPotionEffectEvent.class, this::onEffect);
	}

	private void onEffect(EntityPotionEffectEvent event, MorphPlayer morphPlayer) {
		if (event.getCause() == Cause.FOOD && event.getModifiedType().equals(PotionEffectType.HUNGER)) {
			event.setCancelled(true);
		}
	}

}
