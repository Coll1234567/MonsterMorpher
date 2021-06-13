package me.jishuna.monstermorpher.api.player;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import me.jishuna.commonlib.events.EventConsumer;
import me.jishuna.monstermorpher.MonsterMorpher;
import me.jishuna.monstermorpher.api.utils.Utils;
import me.jishuna.monstermorpher.gson.PlayerDataDeserializer;

public class PlayerManager {
	private final MonsterMorpher plugin;
	private final Map<UUID, MorphPlayer> players = new HashMap<>();
	private final PlayerDataDeserializer playerDataDeserializer;

	public PlayerManager(MonsterMorpher plugin) {
		this.plugin = plugin;

		File playerDataDirectory = new File(this.plugin.getDataFolder() + File.separator + "playerdata");

		if (!playerDataDirectory.exists()) {
			playerDataDirectory.mkdirs();
		}

		this.playerDataDeserializer = new PlayerDataDeserializer(plugin);
	}

	public void registerListeners() {
		EventConsumer<PlayerJoinEvent> loginWrapper = new EventConsumer<>(PlayerJoinEvent.class, event -> {
			loadPlayerData(event.getPlayer());
			event.getPlayer().getInventory().addItem(Utils.getAbilityItem());
		});
		loginWrapper.register(plugin);

		EventConsumer<PlayerQuitEvent> loggoutWrapper = new EventConsumer<>(PlayerQuitEvent.class,
				event -> savePlayerData(event.getPlayer().getUniqueId()));
		loggoutWrapper.register(plugin);
	}

	public Optional<MorphPlayer> getPlayer(Player player) {
		return getPlayer(player.getUniqueId());
	}

	public Optional<MorphPlayer> getPlayer(UUID id) {
		return Optional.ofNullable(this.players.get(id));
	}

	public void savePlayer(UUID id, MorphPlayer player, boolean disable) {
		File jsonFile = new File(this.plugin.getDataFolder() + File.separator + "playerdata", id + ".json");
		if (!jsonFile.exists()) {
			try {
				jsonFile.createNewFile();
			} catch (IOException e) {
				this.plugin.getLogger()
						.severe("Encountered " + e.getClass().getSimpleName() + " while creating player data file.");
				e.printStackTrace();
			}
		}

		if (jsonFile.exists()) {
			player.savePlayer(jsonFile);
		}
	}

	public void saveAllPlayers(boolean disable) {
		this.players.entrySet().forEach(entry -> savePlayer(entry.getKey(), entry.getValue(), disable));

		if (disable) {
			this.players.clear();
		}
	}

	public void loadPlayerData(Player player) {
		UUID uuid = player.getUniqueId();

		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			MorphPlayer morphPlayer = new MorphPlayer(player, this.plugin);
			File jsonFile = new File(this.plugin.getDataFolder() + File.separator + "playerdata", uuid + ".json");

			if (jsonFile.exists()) {
				Gson gson = new GsonBuilder()
						.registerTypeAdapter(PersistantPlayerData.class, this.playerDataDeserializer).create();

				try (FileReader reader = new FileReader(jsonFile)) {
					PersistantPlayerData playerData = gson.fromJson(reader, PersistantPlayerData.class);

					morphPlayer.setPlayerData(playerData);

				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					this.plugin.getLogger()
							.severe("Encountered " + e.getClass().getSimpleName() + " while loading player data.");
					e.printStackTrace();
				}
			} else {
				morphPlayer.setPlayerData(new PersistantPlayerData());
			}

			this.players.put(uuid, morphPlayer);
			morphPlayer.setLoaded(true);
		});

	}

	private void savePlayerData(UUID uuid) {
		MorphPlayer MorphPlayer = this.players.get(uuid);
		if (MorphPlayer != null) {

			Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
				savePlayer(uuid, MorphPlayer, true);
			});
		}
	}

}
