package me.jishuna.monstermorpher;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.jishuna.monstermorpher.api.player.MorphPlayer;
import me.jishuna.monstermorpher.api.player.PlayerManager;

public class MorphUpdateRunnable extends BukkitRunnable {

	private final PlayerManager playerManager;

	public MorphUpdateRunnable(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Optional<MorphPlayer> morphPlayerOptional = this.playerManager.getPlayer(player);

			if (morphPlayerOptional.isPresent()) {
				MorphPlayer morphPlayer = morphPlayerOptional.get();
				if (!morphPlayer.isLoaded())
					continue;

				morphPlayer.tick();
			}
		}
	}

}
