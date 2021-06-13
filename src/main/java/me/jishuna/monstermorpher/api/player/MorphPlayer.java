package me.jishuna.monstermorpher.api.player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import me.jishuna.monstermorpher.MonsterMorpher;
import me.jishuna.monstermorpher.api.abilities.Ability;
import me.jishuna.monstermorpher.api.morph.Morph;
import me.jishuna.monstermorpher.gson.PlayerDataSeralizer;
import net.md_5.bungee.api.ChatColor;

public class MorphPlayer {

	private final DateFormat dateFormat = new SimpleDateFormat("mm:ss");
	private final DateFormat dateFormatHours = new SimpleDateFormat("HH:mm:ss");

	private final BossBar bossbar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
	private final UUID id;
	private final MonsterMorpher plugin;
	private boolean isLoaded = false;

	private Map<String, Long> cooldowns = new HashMap<>();

	private PersistantPlayerData playerData;

	public MorphPlayer(Player player, MonsterMorpher plugin) {
		this.plugin = plugin;
		this.id = player.getUniqueId();

		this.bossbar.setVisible(false);
		this.bossbar.addPlayer(player);
	}

	public UUID getId() {
		return id;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.id);
	}

	public Optional<Morph> getMorph() {
		return this.playerData.getActiveMorph();
	}

	public void setMorph(Morph morph) {
		updateBossBar(morph);

		if (morph != null) {
			setTimeLeft(morph.getDuration());
			morph.onStart(getPlayer());
		} else {
			this.playerData.getActiveMorph().ifPresent(currentMorph -> currentMorph.onEnd(getPlayer()));
		}
		this.playerData.setActiveMorph(morph);
	}

	private void updateBossBar(Morph morph) {
		if (morph == null) {
			this.bossbar.setVisible(false);
		} else {
			this.bossbar.setVisible(true);
		}
	}

	public long getTimeLeft() {
		return this.playerData.getTimeLeft();
	}

	public void setTimeLeft(long time) {
		this.playerData.setTimeLeft(time);
	}

	public long getCooldown(Ability ability) {
		return this.cooldowns.getOrDefault(ability.getKey(), 0L);
	}

	public void setCooldown(Ability ability, long time) {
		this.cooldowns.put(ability.getKey(), time);
	}

	public void setPlayerData(PersistantPlayerData playerData) {
		this.playerData = playerData;
		Bukkit.getScheduler().runTask(this.plugin, () -> {
			playerData.getActiveMorph().ifPresent(morph -> {
				updateBossBar(morph);
				morph.onStart(getPlayer());
			});
		});
	}

	public void tick() {
		Optional<Morph> morphOptional = getMorph();

		if (!morphOptional.isPresent())
			return;

		Morph morph = morphOptional.get();

		long timeLeft = getTimeLeft();
		setTimeLeft(timeLeft - 5);

		if (timeLeft <= 0) {
			setMorph(null);
		} else {
			this.bossbar.setProgress(Math.min(timeLeft / (double) morph.getDuration(), 1));
			// TODO translate
			this.bossbar.setTitle(ChatColor.GREEN + "Morph Time: " + formatTime(timeLeft * 50));
		}
	}

	public void savePlayer(File file) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(PersistantPlayerData.class, PlayerDataSeralizer.PLAYER_SERIALIZER).create();
		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(this.playerData, writer);
		} catch (JsonIOException | IOException e) {
			// this.plugin.getLogger().severe("Encountered " + e.getClass().getSimpleName()
			// + " while saving player data for UUID " + this.id);
		}
	}

	private String formatTime(long time) {

		if (time < 60 * 60 * 1000) {
			return dateFormat.format(new Date(time));
		} else {
			return dateFormatHours.format(new Date(time));
		}
	}
}
