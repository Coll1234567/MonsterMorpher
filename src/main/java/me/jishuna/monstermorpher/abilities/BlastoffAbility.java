package me.jishuna.monstermorpher.abilities;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.player.MorphPlayer;
import me.jishuna.monstermorpher.api.utils.Utils;

public class BlastoffAbility extends Ability {
	private static final String KEY = "blastoff";

	public BlastoffAbility(Plugin owner) {
		super(KEY, loadConfig(owner, KEY));

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event, MorphPlayer morphPlayer) {
		if (!Utils.isAbilityItem(event.getItem()) || !checkCooldown(morphPlayer))
			return;

		Player player = event.getPlayer();

		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
			Vector velocity = player.getEyeLocation().getDirection().multiply(2.5);
			velocity.setY(2.5);

			player.setVelocity(velocity);
			player.setNoDamageTicks(1);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 160, 0, true));
			player.getWorld().createExplosion(player.getLocation(), 3, false, false);

			morphPlayer.setCooldown(this, System.currentTimeMillis() + getCooldown() * 50);
		}
	}

}
