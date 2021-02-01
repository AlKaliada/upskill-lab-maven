package com.epam.kaliada.validator;

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
    public static void validateXml(String xml, String xsd) throws SAXException, IOException {
        LOGGER.log(Level.TRACE, "start checking {} as valid with schema {}", xml, xsd);
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(xsd);
        Schema schema = factory.newSchema(schemaLocation);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(xml);
        validator.validate(source);
        LOGGER.log(Level.TRACE, "{} valid", xml);
    }
}
