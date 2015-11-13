package com.android.diego.datanet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

    public static void writeCsvFile(File fileName, List<Node> nodes) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Node node : nodes) {
                fileWriter.append(NODE_ACTIVE.toString());
                fileWriter.append(SEMICOLON_DELIMITER);

                fileWriter.append(node.getName());
                fileWriter.append(SEMICOLON_DELIMITER);

                List<String> values = node.getValues();
                for (int i = 0; i < values.size(); i++) {
                    if (i == values.size() - 1) {
                        fileWriter.append(values.get(i));
                    } else {
                        fileWriter.append(values.get(i));
                        fileWriter.append(COLON_DELIMITER);
                    }
                }
                fileWriter.append(SEMICOLON_DELIMITER);

                fileWriter.append(node.getName());
                fileWriter.append(SEMICOLON_DELIMITER);

                fileWriter.append(node.getName());

                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

}
