package me.jishuna.monstermorpher.api.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.jishuna.monstermorpher.api.morph.Morph;

public class MorphSetupEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final List<Morph> morphsToAdd = new ArrayList<>();

	public List<Morph> getMorphsToAdd() {
		return morphsToAdd;
	}

	@Override
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
