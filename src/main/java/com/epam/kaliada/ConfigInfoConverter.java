package com.epam.kaliada;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigInfoConverter implements JsonSerializer<ConfigInfo>, JsonDeserializer<ConfigInfo> {
    @Override
    public ConfigInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("files").getAsJsonArray();
        Path[] files = new Path[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            files[i] = Paths.get(jsonArray.get(i).getAsString());
        }
        String suffix = jsonObject.get("suffix").getAsString();
        return new ConfigInfo(files, suffix);
    }

    @Override
    public JsonElement serialize(ConfigInfo configInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < configInfo.getFiles().length; i++) {
            jsonObject.addProperty("files", configInfo.getFiles()[i].toString());
        }
        jsonObject.addProperty("suffix", configInfo.getSuffix());
        return jsonObject;
    }
}
