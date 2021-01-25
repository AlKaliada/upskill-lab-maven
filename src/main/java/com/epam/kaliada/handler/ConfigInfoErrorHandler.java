package com.epam.kaliada.handler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class ConfigInfoErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void warning(SAXParseException exception) {
        LOGGER.log(Level.WARN, String.format("%s - %s", getLineColumnNumber(exception), exception.getMessage()));
    }

    @Override
    public void error(SAXParseException exception) {
        LOGGER.log(Level.ERROR, String.format("%s - %s", getLineColumnNumber(exception), exception.getMessage()));
    }

    @Override
    public void fatalError(SAXParseException exception) {
        LOGGER.log(Level.FATAL, String.format("%s - %s", getLineColumnNumber(exception), exception.getMessage()));
    }

    private String getLineColumnNumber(SAXParseException exception){
        return String.format("%s : %s", exception.getLineNumber(), exception.getColumnNumber());
    }

}
