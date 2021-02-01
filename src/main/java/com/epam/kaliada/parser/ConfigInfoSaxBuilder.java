package com.epam.kaliada.parser;

import com.epam.kaliada.entity.ConfigInfo;
import com.epam.kaliada.handler.ConfigInfoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class ConfigInfoSaxBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private ConfigInfo configInfo;
    private ConfigInfoHandler handler = new ConfigInfoHandler();
    private XMLReader reader;

    public ConfigInfoSaxBuilder() throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        reader = saxParser.getXMLReader();
        reader.setContentHandler(handler);
    }

    public ConfigInfo getConfigInfo() {
        return configInfo;
    }

    public void buildConfigInfo(String filename) throws IOException, SAXException {
        reader.parse(filename);
        configInfo = handler.getConfigInfo();
    }
}
