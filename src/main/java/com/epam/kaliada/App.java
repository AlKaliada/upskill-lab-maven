package com.epam.kaliada;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;


public class App 
{
    public static void main(String[] args)  {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name = reader.readLine();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ConfigInfo.class, new ConfigInfoConverter());
            Gson gson = gsonBuilder.create();

            JsonReader jsonReader = new JsonReader(new FileReader(name));
            ConfigInfo configInfo = gson.fromJson(jsonReader, ConfigInfo.class);

            if (configInfo != null){
                Renamer renamer = new Renamer();
                renamer.renameFiles(configInfo.getFiles(), configInfo.getSuffix());
            }else {
                System.out.println("File " + name + " is empty");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
