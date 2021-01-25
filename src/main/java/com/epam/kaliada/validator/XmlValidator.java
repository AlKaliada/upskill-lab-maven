package com.epam.kaliada.validator;

import com.epam.kaliada.handler.ConfigInfoErrorHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void validateXml(String xml, String xsd){
        LOGGER.log(Level.TRACE, String.format("start checking %s as valid with schema %s", xml, xsd));
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(xsd);
        try{
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xml);
            validator.setErrorHandler(new ConfigInfoErrorHandler());
            validator.validate(source);
            LOGGER.log(Level.TRACE, String.format("%s valid", xml));
        }catch (SAXException | IOException e){
            LOGGER.log(Level.WARN, String.format("%s is not valid", xml), e);
        }
    }
}
