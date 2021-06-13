package me.jishuna.monstermorpher.api.abilities;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public enum ActivationType {
	LEFT, SHIFT_LEFT, RIGHT, SHIFT_RIGHT, PASSIVE, CONSTANT;

	public static ActivationType getFromEvent(PlayerInteractEvent event) {
		boolean shift = event.getPlayer().isSneaking();

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			return shift ? SHIFT_RIGHT : RIGHT;
		} else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			return shift ? SHIFT_LEFT : LEFT;
		}
		return RIGHT;
	}
}
