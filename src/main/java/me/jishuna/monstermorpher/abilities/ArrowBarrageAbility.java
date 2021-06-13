package me.jishuna.monstermorpher.abilities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.player.MorphPlayer;
import me.jishuna.monstermorpher.api.utils.Utils;

public class ArrowBarrageAbility extends Ability {
	private static final String KEY = "arrow_barrage";

	public ArrowBarrageAbility(Plugin owner) {
		super(KEY, loadConfig(owner, KEY));

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event, MorphPlayer morphPlayer) {
		if (!Utils.isAbilityItem(event.getItem()) || !checkCooldown(morphPlayer))
			return;

		Player player = event.getPlayer();

		Random random = ThreadLocalRandom.current();
		for (int i = 0; i < 20; i++) {
			Vector toAdd = new Vector((random.nextDouble() - 0.5) / 4, (random.nextDouble() - 0.5) / 5,
					(random.nextDouble() - 0.5) / 4);
			Arrow arrow = player.launchProjectile(Arrow.class,
					player.getEyeLocation().getDirection().add(toAdd).multiply(2));
			arrow.setPickupStatus(PickupStatus.CREATIVE_ONLY);
			arrow.setTicksLived(1000);
		}

		morphPlayer.setCooldown(this, System.currentTimeMillis() + getCooldown() * 50);
	}

}
