package me.jishuna.monstermorpher.api.event;

import java.util.function.BiConsumer;

import org.bukkit.event.Event;

import me.jishuna.monstermorpher.api.player.MorphPlayer;

public class EventWrapper<T extends Event> {

	private BiConsumer<T, MorphPlayer> handler;
	private Class<T> eventClass;

	public EventWrapper(Class<T> eventClass, BiConsumer<T, MorphPlayer> handler) {
		this.handler = handler;
		this.eventClass = eventClass;
	}

	public void consume(Event event, MorphPlayer morphPlayer) {
		if (this.eventClass.isAssignableFrom(event.getClass())) {
			handler.accept(this.eventClass.cast(event), morphPlayer);
		}
	}
}
