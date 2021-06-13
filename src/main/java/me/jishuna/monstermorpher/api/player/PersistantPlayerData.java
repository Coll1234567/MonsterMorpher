package me.jishuna.monstermorpher.api.player;

import java.util.Optional;

import me.jishuna.monstermorpher.api.morph.Morph;

public class PersistantPlayerData {

	private Morph activeMorph;
	private long timeLeft;

	public PersistantPlayerData() {
		activeMorph = null;
		timeLeft = -1;
	}

	public PersistantPlayerData(Morph morph, long time) {
		this.activeMorph = morph;
		this.timeLeft = time;
	}

	public Optional<Morph> getActiveMorph() {
		return Optional.ofNullable(activeMorph);
	}

	public void setActiveMorph(Morph activeMorph) {
		this.activeMorph = activeMorph;
	}

	public long getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(long timeLeft) {
		this.timeLeft = timeLeft;
	}

}
