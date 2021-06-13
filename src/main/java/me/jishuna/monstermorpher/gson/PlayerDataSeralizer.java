package me.jishuna.monstermorpher.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.jishuna.monstermorpher.api.player.PersistantPlayerData;

public class PlayerDataSeralizer implements JsonSerializer<PersistantPlayerData> {
	public static final PlayerDataSeralizer PLAYER_SERIALIZER = new PlayerDataSeralizer();

	@Override
	public JsonElement serialize(PersistantPlayerData data, Type typeOfT, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		if (data.getActiveMorph().isPresent()) {
			json.add("morph", context.serialize(data.getActiveMorph().get().getKey()));

			json.add("time", context.serialize(data.getTimeLeft()));
		}

		return json;
	}
}