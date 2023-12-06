package com.example.javaapi200543216;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HomeController {
    // Assuming you have a class level variable to hold your JSON string
    private JSONArray animalsJsonArray = new JSONArray();

    @FXML
    private TextField animalNameTextField;

    @FXML
    private ListView<String> animalsListView;

    private Animals mainApp;
    private DetailsController detailsController;

    // Reference to the main application
    public void setMainApp(Animals mainApp) {
        this.mainApp = mainApp;
    }

    // Set the second scene controller
    public void setDetailsController(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    // Method to set the JSON response (for example, in your HTTP request method)
    private void setAnimalsJsonArray(JSONArray jsonArray) {
        this.animalsJsonArray = jsonArray;
    }

    private Optional<JSONObject> findJsonObjectByName(String name) {
        for (int i = 0; i < animalsJsonArray.length(); i++) {
            JSONObject jsonObject = animalsJsonArray.getJSONObject(i);
            String animalNameFromJson = jsonObject.optString("name", "");

            if (animalNameFromJson.equals(name)) {
                return Optional.of(jsonObject);
            }
        }

        return Optional.empty();
    }

    @FXML
    private void handleGetInfoButtonClick() {
        String apiKey = "vx6VNkE8Do7iHftxWoyPlw==yLBDyAnUFQT3CB11";
        String animalName = animalNameTextField.getText();

        try {
            URL url = new URL("https://api.api-ninjas.com/v1/animals?name=" + animalName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", apiKey);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response into a JSONArray
                JSONArray jsonArray = new JSONArray(response.toString());
                // Clear existing items in the ListView
                animalsListView.getItems().clear();

                // Loop through each JSONObject in the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Get the current JSONObject
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Extract the "name" property from the JSONObject
                    String animalNameProp = jsonObject.optString("name", "");

                    // Add the animal name to the ListView
                    animalsListView.getItems().add(animalNameProp);
                }

                // Set the JSON response
                setAnimalsJsonArray(jsonArray);
            } else {
                System.out.println("Error: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleListViewItemClick() {
        String selectedItem = animalsListView.getSelectionModel().getSelectedItem();
        Optional<JSONObject> optionalJsonObject = findJsonObjectByName(selectedItem);

        // Check if the JSONObject is present in the Optional
        if (optionalJsonObject.isPresent()) {
            // Unwrap the JSONObject from the Optional
            JSONObject jsonObject = optionalJsonObject.get();

            // Pass the JSONObject to showDetailsScene
            mainApp.showDetailsScene(jsonObject);
        }
    }

    // Method to retrieve the first scene
    public Scene getScene1() {
        return animalNameTextField.getScene();
    }

    // Method to create the second scene dynamically
    public Scene getScene2(JSONObject selectedItemJson, Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Animals.class.getResource("details-view.fxml"));
        Scene scene2 = new Scene(loader.load(), 640, 480);

        // Pass the selected item and primaryStage to the second scene controller
        DetailsController detailsController = loader.getController();
        detailsController.initData(selectedItemJson, primaryStage, this);

        return scene2;
    }
}
