package com.epam.kaliada.transform;

import com.epam.kaliada.App;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class XmlDocumentCreator {
    private static final Logger LOGGER = LogManager.getLogger();
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private String fileConfigName;
    private Map<String, String> renamedFiles;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private int idFile;


    public XmlDocumentCreator(String fileConfigName, Map<String, String> renamedFiles) {
        this.fileConfigName = fileConfigName;
        this.renamedFiles = renamedFiles;
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = documentBuilder.newDocument();
    }

    public void createXmlDocument(){
        LOGGER.log(Level.INFO, "Start creating xml file with results");
        String root = "renaming-info";
        Element rootElement = document.createElement(root);
        document.appendChild(rootElement);

        Element elementName = document.createElement("config-file-name");
        elementName.appendChild(document.createTextNode(fileConfigName));

        Element elementRenamedFiles = document.createElement("renamed-files");

        rootElement.appendChild(elementName);
        rootElement.appendChild(elementRenamedFiles);

        for (Map.Entry<String, String> entry : renamedFiles.entrySet()) {
            Element elementFile = document.createElement("file");
            elementRenamedFiles.appendChild(elementFile);
            elementFile.setAttribute("id", String.valueOf(++idFile));

            Element elementOldName = document.createElement("old-name");
            elementFile.appendChild(elementOldName);
            String oldName = entry.getKey();
            elementOldName.appendChild(document.createTextNode(oldName));

            Element elementNewName = document.createElement("new-name");
            elementFile.appendChild(elementNewName);
            String newName = entry.getValue();
            elementNewName.appendChild(document.createTextNode(newName));

            elementFile.appendChild(elementOldName);
            elementFile.appendChild(elementNewName);
        }
        Element executionTime = document.createElement("execution-time");
        executionTime.appendChild(document.createTextNode(String.valueOf(System.currentTimeMillis() - App.START_TIME)));
        rootElement.appendChild(executionTime);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(String.format("%s.xml", simpleDateFormat.format(new Date()))));
            transformer.transform(source, result);
            LOGGER.log(Level.INFO, "finish creating xml file with results");
        } catch (TransformerException | IOException e){
            LOGGER.log(Level.ERROR, e);
        }
    }
}
