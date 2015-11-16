package com.android.diego.datanet;


import org.w3c.dom.*;
import org.w3c.dom.Node;

import java.io.File;
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
public class XmlFileWriter {

    private NodeStore nodeStore;

    public XmlFileWriter(NodeStore nodeStore) {
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

}
