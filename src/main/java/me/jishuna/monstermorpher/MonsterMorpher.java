package me.jishuna.monstermorpher;

import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.monstermorpher.api.abilities.AbilityManager;
import me.jishuna.monstermorpher.api.listener.EventManager;
import me.jishuna.monstermorpher.api.morph.MorphManager;
import me.jishuna.monstermorpher.api.player.PlayerManager;
import me.jishuna.monstermorpher.commands.MorphTestCommand;

public class MonsterMorpher extends JavaPlugin {

	private PlayerManager playerManager;
	private MorphManager morphManager;
	private EventManager eventManager;
	private AbilityManager abilityManager;

	@Override
	public void onEnable() {
		PluginKeys.initialize(this);
		
		this.abilityManager = new AbilityManager(this);
		this.abilityManager.reloadAbilities();

		this.morphManager = new MorphManager(this);
		this.morphManager.reloadMorphs();

		this.playerManager = new PlayerManager(this);
		this.playerManager.registerListeners();

		this.eventManager = new EventManager(this);

		new MorphUpdateRunnable(playerManager).runTaskTimer(this, 5, 5);
		
		getCommand("morph").setExecutor(new MorphTestCommand(this));
	}

	@Override
	public void onDisable() {
		this.playerManager.saveAllPlayers(true);
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public MorphManager getMorphManager() {
		return morphManager;
	}

	public AbilityManager getAbilityManager() {
		return abilityManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

}
