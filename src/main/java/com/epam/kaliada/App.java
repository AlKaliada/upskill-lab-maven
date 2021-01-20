package com.epam.kaliada;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class App {
    private final static Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args)  {
        LOGGER.log(Level.INFO, "Application started");
        String name = new String();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();
            LOGGER.log(Level.INFO, "read config file name: " + name);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ConfigInfo.class, new ConfigInfoConverter());
            Gson gson = gsonBuilder.create();

            JsonReader jsonReader = new JsonReader(new FileReader(name));
            LOGGER.log(Level.INFO, "start reading file " + name);
            ConfigInfo configInfo = gson.fromJson(jsonReader, ConfigInfo.class);
            LOGGER.log(Level.INFO, "finish reading file " + name);

            if (configInfo != null){
                Renamer renamer = new Renamer();
                LOGGER.log(Level.INFO, "start renaming files");
                renamer.renameFiles(configInfo.getFiles(), configInfo.getSuffix());
                LOGGER.log(Level.INFO, "application finished working");
            }else {
                LOGGER.log(Level.WARN, "File " + name + " is empty");
            }
        }catch (FileNotFoundException e){
            LOGGER.log(Level.ERROR, "file " + name + " not found", e);
        }catch (IOException e){
            LOGGER.log(Level.ERROR, "problem with read config file name or renaming", e);
        }
    }
}
