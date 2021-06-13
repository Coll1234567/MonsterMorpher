package me.jishuna.monstermorpher.api.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.jishuna.monstermorpher.api.abilities.Ability;

public class AbilitySetupEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final List<Ability> abilitiesToAdd = new ArrayList<>();

	public List<Ability> getAbilitiesToAdd() {
		return abilitiesToAdd;
	}

	@Override
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
