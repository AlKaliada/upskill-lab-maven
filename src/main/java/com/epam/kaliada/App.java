package com.epam.kaliada;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class App {
    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args)  {
        logger.log(Level.INFO, "Application started");
        String name = new String();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();
            logger.log(Level.INFO, "read config file name: " + name);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ConfigInfo.class, new ConfigInfoConverter());
            Gson gson = gsonBuilder.create();

            JsonReader jsonReader = new JsonReader(new FileReader(name));
            logger.log(Level.INFO, "start reading file " + name);
            ConfigInfo configInfo = gson.fromJson(jsonReader, ConfigInfo.class);
            logger.log(Level.INFO, "finish reading file " + name);

            if (configInfo != null){
                Renamer renamer = new Renamer();
                logger.log(Level.INFO, "start renaming files");
                renamer.renameFiles(configInfo.getFiles(), configInfo.getSuffix());
                logger.log(Level.INFO, "application finished working");
            }else {
                logger.log(Level.WARN, "File " + name + " is empty");
            }
        }catch (FileNotFoundException e){
            logger.log(Level.ERROR, "file " + name + " not found", e);
        }catch (IOException e){
            logger.log(Level.ERROR, "problem with read config file name or renaming", e);
        }
    }
}
