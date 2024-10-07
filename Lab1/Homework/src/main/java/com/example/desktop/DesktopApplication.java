package com.example.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DesktopApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextArea responseArea = new TextArea();
        responseArea.setEditable(false);

        // Input fields for number of vertices and edges
        TextField numVerticesField = new TextField();
        numVerticesField.setPromptText("Enter number of vertices");
        TextField numEdgesField = new TextField();
        numEdgesField.setPromptText("Enter number of edges");

        // Create button to send the request to servlet
        Button invokeButton = new Button("Invoke Servlet");
        invokeButton.setOnAction(e -> {
            String numVertices = numVerticesField.getText();
            String numEdges = numEdgesField.getText();

            String response = invokeServlet(numVertices, numEdges);
            responseArea.setText(response);

        });

        // Create layout for interface
        VBox vbox = new VBox(10, new Label("Number of Vertices:"), numVerticesField,
                new Label("Number of Edges:"), numEdgesField, invokeButton, responseArea);
        Scene scene = new Scene(vbox, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Generate Adjacency Matrix");
        primaryStage.show();
    }


    private String invokeServlet(String numVertices, String numEdges) {
        try {
            // Send parameters numVertices and numEdges in the URL
            String urlString = String.format("http://localhost:8080/Homework_war_exploded/generateGraph?numVertices=%s&numEdges=%s",
                    numVertices, numEdges);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            return extractMatrixFromHtml(content.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return "Error invoking servlet!";
        }
    }

    private String extractMatrixFromHtml(String htmlResponse) {
        StringBuilder matrix = new StringBuilder();

        // Find the table tag and extract the rows
        int tableStart = htmlResponse.indexOf("<table");
        int tableEnd = htmlResponse.indexOf("</table>");

        if (tableStart != -1 && tableEnd != -1) {
            String tableContent = htmlResponse.substring(tableStart, tableEnd);

            // Extract each row (tr) from the table
            String[] rows = tableContent.split("<tr>");
            for (String row : rows) {
                if (row.contains("<td>")) {
                    // Extract each cell (td) from the row
                    String[] cells = row.split("<td>");
                    for (String cell : cells) {
                        if (cell.contains("</td>")) {
                            // Extract the value inside the <td> tag
                            String cellValue = cell.substring(0, cell.indexOf("</td>")).trim();
                            matrix.append(cellValue).append(" ");
                        }
                    }
                    matrix.append("\n");
                }
            }
        }

        return matrix.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
