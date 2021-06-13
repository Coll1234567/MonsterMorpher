package me.jishuna.monstermorpher.abilities;

import org.bukkit.entity.Arrow;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.player.MorphPlayer;

public class TestAbility extends Ability {
	private static final String KEY = "test";

	public TestAbility(Plugin owner) {
		super(KEY, loadConfig(owner, KEY));

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event, MorphPlayer player) {
		if (!checkCooldown(player))
			return;
		
		event.getPlayer().launchProjectile(Arrow.class);
		
		player.setCooldown(this, System.currentTimeMillis() + getCooldown() * 50);
	}

}
