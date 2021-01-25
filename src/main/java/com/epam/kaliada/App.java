package com.epam.kaliada;


import com.epam.kaliada.entity.ConfigInfo;
import com.epam.kaliada.parser.ConfigInfoSaxBuilder;
import com.epam.kaliada.service.Renamer;
import com.epam.kaliada.transform.XmlDocumentCreator;
import com.epam.kaliada.validator.XmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class App {
    private final static Logger LOGGER = LogManager.getLogger();
    public static final long START_TIME = System.currentTimeMillis();

    public static void main(String[] args)  {
        LOGGER.log(Level.INFO, "program starts working");
        try {
            String xml = "files/config.xml";
            String xsd = "files/config.xsd";
            XmlValidator.validateXml(xml, xsd);

            ConfigInfoSaxBuilder saxBuilder = new ConfigInfoSaxBuilder();
            saxBuilder.buildConfigInfo(xml);
            ConfigInfo configInfo = saxBuilder.getConfigInfo();

            Renamer renamer = new Renamer();
            renamer.renameFiles(configInfo.getFiles(), configInfo.getSuffix());
            XmlDocumentCreator xmlDocumentCreator = new XmlDocumentCreator(xml, renamer.getRenamedFiles());
            xmlDocumentCreator.createXmlDocument();

        }catch (IOException e){
            LOGGER.log(Level.ERROR, "problem with read config file name or renaming", e);
        }
        LOGGER.log(Level.INFO, "program finish working");
    }
}
