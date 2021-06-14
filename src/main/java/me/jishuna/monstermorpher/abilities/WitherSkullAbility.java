package me.jishuna.monstermorpher.abilities;

import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.player.MorphPlayer;
import me.jishuna.monstermorpher.api.utils.Utils;

public class WitherSkullAbility extends Ability {
	private static final String KEY = "wither_skull";

	public WitherSkullAbility(Plugin owner) {
		super(KEY, loadConfig(owner, KEY));

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event, MorphPlayer morphPlayer) {
		if (!Utils.isAbilityItem(event.getItem()) || !checkCooldown(morphPlayer))
			return;

		event.getPlayer().launchProjectile(WitherSkull.class);

		morphPlayer.setCooldown(this, System.currentTimeMillis() + getCooldown() * 50);
	}

}
