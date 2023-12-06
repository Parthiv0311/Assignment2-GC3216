package com.example.javaapi200543216;

import org.json.JSONArray;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONObject;

public class DetailsController {

    @FXML
    private Label nameLabel, scientificNameLabel, locationsLabel, speciesLabel, nameOfYoungLabel, lifespanLabel;

    private Stage primaryStage;
    private HomeController homeController;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    // Method to initialize data in the second scene
    public void initData(JSONObject animalData, Stage primaryStage, HomeController homeController) {
        setHomeController(homeController);
        this.primaryStage = primaryStage;
        String name = animalData.optString("name", "");
        nameLabel.setText(name);

        JSONObject taxonomy = animalData.optJSONObject("taxonomy");
        if (taxonomy != null) {
            scientificNameLabel.setText("Scientific Name: " + taxonomy.optString("scientific_name", ""));
        }

        JSONArray locations = animalData.optJSONArray("locations");
        if (locations != null && locations.length() > 0) {
            locationsLabel.setText("Locations: " + locations.optString(0, ""));
        }

        JSONObject characteristics = animalData.optJSONObject("characteristics");
        if (characteristics != null) {
            speciesLabel.setText("Number of Species: " + characteristics.optString("number_of_species", ""));
            nameOfYoungLabel.setText("Name of Young: " + characteristics.optString("name_of_young", ""));
            lifespanLabel.setText("Lifespan: " + characteristics.optString("lifespan", ""));
            // Add more properties as needed
        }
    }

    // Method to handle the back button click
    @FXML
    public void handleBackButtonClick() {
        // Switch back to the first scene
        Scene scene1 = homeController.getScene1();
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
