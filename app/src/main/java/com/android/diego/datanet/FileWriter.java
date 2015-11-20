package com.android.diego.datanet;


import org.w3c.dom.*;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Created by Diego on 16/11/2015.
 */
public class FileWriter {

    private NodeStore nodeStore;

    public FileWriter(NodeStore nodeStore) {
        this.nodeStore = nodeStore;
    }

    public void writeXmlFile(File fileName) {
        List<com.android.diego.datanet.Node> nodes = nodeStore.getNodes();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //add elements to Document
            Element rootElement = doc.createElement("nodes");
            //append root element to document
            doc.appendChild(rootElement);

            //append first child element to root element


            for (com.android.diego.datanet.Node node : nodes) {
                rootElement.appendChild(newNode(doc, node));
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "6");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileName);

            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private Node newNode(Document doc, com.android.diego.datanet.Node node) {
        //node element
        Element nodeEl = doc.createElement("node");

        //set attribute to node element
        nodeEl.setAttribute("id", node.getName());

        //value elements
        List<String> values = node.getValues();

        for (String val : values) {
            Element value = doc.createElement("value");
            value.appendChild(doc.createTextNode(val));
            nodeEl.appendChild(value);
        }

        //parent elements
        List<UUID> parents = node.getParents();
        if (!parents.isEmpty()) {
            Element parent = doc.createElement("parents");
            nodeEl.appendChild(parent);

            for (UUID par: parents) {
                com.android.diego.datanet.Node nodeP = nodeStore.getNode(par);
                parent.appendChild(newNode(doc, nodeP));
            }
        }

        //probability elements
        List<Double> prob = node.getProbabilities();
        String sProbabilities = "";

        for (int i = 0; i < prob.size(); i++) {
            if (i == prob.size() - 1) {
                sProbabilities += prob.get(i);
            } else {
                sProbabilities += prob.get(i) + " ";
            }
        }

        Element probabilities = doc.createElement("probabilities");
        probabilities.appendChild(doc.createTextNode(sProbabilities));
        nodeEl.appendChild(probabilities);

        return nodeEl;
    }

    public void writeCsvFile(File fileName) {
        final String SEMICOLON_DELIMITER = ";";
        final String COLON_DELIMITER = ":";

        //CSV file header
        final String FILE_HEADER = "attivo;nodo;valori;padri;probability";

        final String NODE_ACTIVE = "x";

        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file");
            System.exit(0);
        }

        List<com.android.diego.datanet.Node> nodes = nodeStore.getNodes();

        //Write the CSV file header
        outputStream.println(FILE_HEADER);

        //Write a new node object list to the CSV file
        for (com.android.diego.datanet.Node node : nodes) {
            //Write if the node is active
            outputStream.print(NODE_ACTIVE);
            outputStream.print(SEMICOLON_DELIMITER);

            //Write the name of the node
            outputStream.print(node.getName());
            outputStream.print(SEMICOLON_DELIMITER);

            //Write the values of the node
            List<String> values = node.getValues();
            for (int i = 0; i < values.size(); i++) {
                if (i == values.size() - 1) {
                    outputStream.print(values.get(i));
                } else {
                    outputStream.print(values.get(i));
                    outputStream.print(COLON_DELIMITER);
                }
            }
            outputStream.print(SEMICOLON_DELIMITER);

            //Write the parents of the node
            List<UUID> parents = node.getParents();
            for (int i = 0; i < parents.size(); i++) {
                if (i == parents.size() - 1) {
                    outputStream.print(nodeStore.getNode(parents.get(i)).getName());
                } else {
                    outputStream.print(nodeStore.getNode(parents.get(i)).getName());
                    outputStream.print(COLON_DELIMITER);
                }
            }
            outputStream.print(SEMICOLON_DELIMITER);

            //Write the probabilities of the node
            List<Double> probabilities = node.getProbabilities();
            for (int i = 0; i < probabilities.size(); i++) {
                if (i == probabilities.size() - 1) {
                    outputStream.print(probabilities.get(i));
                } else {
                    outputStream.print(probabilities.get(i));
                    outputStream.print(COLON_DELIMITER);
                }
            }
            outputStream.println();
        }
        outputStream.close();
    }

}
