package com.example.javaapi200543216;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class Animals extends Application {
    private Stage primaryStage;
    private HomeController homeController;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        // Load the logo image
        Image logo = new Image("logo.png");

        // Load the first scene
        FXMLLoader helloLoader = new FXMLLoader(Animals.class.getResource("home-view.fxml"));
        Parent helloRoot = helloLoader.load();
        homeController = helloLoader.getController();

        Scene scene1 = new Scene(helloRoot, 640, 480);

        // Set the controller for the first scene
        homeController.setMainApp(this);

        // Load the second scene (details-view.fxml)
        FXMLLoader detailsLoader = new FXMLLoader(Animals.class.getResource("details-view.fxml"));
        Parent detailsRoot = detailsLoader.load();
        DetailsController detailsController = detailsLoader.getController();

        // Set the second scene controller in the first scene controller
        homeController.setDetailsController(detailsController);

        // Set the HelloController reference in DetailsController
        detailsController.setHomeController(homeController);

        Scene scene2 = new Scene(detailsRoot, 400, 300);

        // Set the second scene controller in the first scene controller
        homeController.setDetailsController(detailsController);

        // Set the initial scene
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Animals DB");
        primaryStage.getIcons().add(logo);

        primaryStage.show();
    }

    // Method to switch to the second scene
    public void showDetailsScene(JSONObject selectedItemJson) {
        try {
            primaryStage.setScene(homeController.getScene2(selectedItemJson, primaryStage));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
