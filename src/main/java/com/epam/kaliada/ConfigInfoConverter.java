package com.epam.kaliada;

import com.google.gson.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigInfoConverter implements JsonSerializer<ConfigInfo>, JsonDeserializer<ConfigInfo> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public ConfigInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        logger.log(Level.INFO, "start parsing from json file");
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("files").getAsJsonArray();
        Path[] files = new Path[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            files[i] = Paths.get(jsonArray.get(i).getAsString());
        }
        String suffix = jsonObject.get("suffix").getAsString();
        logger.log(Level.INFO, "finish parsing from json file");
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
