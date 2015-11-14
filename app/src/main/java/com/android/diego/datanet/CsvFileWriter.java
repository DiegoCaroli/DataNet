package com.android.diego.datanet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/**
 * Created by Diego on 13/11/2015.
 */
public class CsvFileWriter {

    //Delimiter used in CSV file
    private static final String SEMICOLON_DELIMITER = ";";
    private static final String COLON_DELIMITER = ":";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "attivo;nodo;valori;padri;probability";

    private static final String NODE_ACTIVE = "x";

    public static void writeCsvFile(File fileName, NodeStore nodeStore) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file");
            System.exit(0);
        }

        List<Node> nodes = nodeStore.getNodes();

        //Write the CSV file header
        outputStream.println(FILE_HEADER);

        //Write a new node object list to the CSV file
        for (Node node : nodes) {
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
