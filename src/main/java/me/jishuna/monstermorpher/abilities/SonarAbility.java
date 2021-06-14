package me.jishuna.monstermorpher.abilities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.player.MorphPlayer;
import me.jishuna.monstermorpher.api.utils.Utils;

public class SonarAbility extends Ability {
	private static final String KEY = "sonar";

	public SonarAbility(Plugin owner) {
		super(KEY, loadConfig(owner, KEY));

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event, MorphPlayer morphPlayer) {
		if (!Utils.isAbilityItem(event.getItem()) || !checkCooldown(morphPlayer))
			return;

		for (Entity entity : event.getPlayer().getNearbyEntities(25, 25, 25)) {
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0, true));
			}
		}
		
		morphPlayer.setCooldown(this, System.currentTimeMillis() + getCooldown() * 50);
	}

}
