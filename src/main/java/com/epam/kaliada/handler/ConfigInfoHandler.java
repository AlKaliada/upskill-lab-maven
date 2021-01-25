package com.epam.kaliada.handler;

import com.epam.kaliada.entity.ConfigInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class ConfigInfoHandler extends DefaultHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private ConfigInfo configInfo;
    private ConfigInfoXmlTag currentXmlTag;
    private EnumSet<ConfigInfoXmlTag> withText;
    private Path currentPath;
    private StringBuilder currentBuilder;

    public ConfigInfoHandler() {
        configInfo = new ConfigInfo();
        withText = EnumSet.range(ConfigInfoXmlTag.PATH, ConfigInfoXmlTag.SUFFIX);
    }

    public ConfigInfo getConfigInfo() {
        return configInfo;
    }

    @Override
    public void startDocument()  {
        LOGGER.log(Level.INFO, "start parsing xml file");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        LOGGER.log(Level.TRACE, String.format("start parsing element %s", qName));
        try{
            ConfigInfoXmlTag temp = ConfigInfoXmlTag.valueOf(qName.toUpperCase());
            if (withText.contains(temp)){
                currentXmlTag = temp;
                currentBuilder = new StringBuilder();
            }
        }catch (IllegalArgumentException e){
            LOGGER.log(Level.ERROR, e);
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentBuilder != null){
            currentBuilder.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String data = currentBuilder.toString();
        if (currentXmlTag != null){
            switch (currentXmlTag){
                case PATH:
                    currentPath = Paths.get(data);
                    configInfo.addFile(currentPath);
                    break;
                case SUFFIX:
                    configInfo.setSuffix(data);
                    break;
                default:
                    throw new IllegalArgumentException("j");
            }
        }
        currentXmlTag = null;
        LOGGER.log(Level.TRACE, String.format("finish parsing element %s", qName));
    }

    @Override
    public void endDocument() {
        LOGGER.log(Level.INFO, "finish parsing xml file");
    }
}
