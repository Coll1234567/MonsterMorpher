package me.jishuna.monstermorpher.gson;

import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import me.jishuna.monstermorpher.MonsterMorpher;
import me.jishuna.monstermorpher.api.morph.Morph;
import me.jishuna.monstermorpher.api.player.PersistantPlayerData;

public class PlayerDataDeserializer implements JsonDeserializer<PersistantPlayerData> {

	private final MonsterMorpher plugin;

	public PlayerDataDeserializer(MonsterMorpher plugin) {
		this.plugin = plugin;
	}

	public PersistantPlayerData deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject json = jsonElement.getAsJsonObject();

		long time = -1;

		if (json.has("time")) {
			time = context.deserialize(json.get("time"), Long.class);
		}

		Morph morph = null;

		if (json.has("morph")) {
			Optional<Morph> morphOptional = plugin.getMorphManager()
					.getMorph(context.deserialize(json.get("morph"), String.class));

			if (morphOptional.isPresent()) {
				morph = morphOptional.get();
			}
		}

		return new PersistantPlayerData(morph, time);
	}
}