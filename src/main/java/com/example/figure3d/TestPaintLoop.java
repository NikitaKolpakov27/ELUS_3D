package com.example.figure3d;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestPaintLoop extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a button
        Button button = new Button("Update Screen");

        // Create a label to update
        Label label = new Label("Initial Text");

        Sphere sphere = new Sphere(10);

        // Create a layout
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(button, sphere, label);

        // Set up the event handler for the button
        button.setOnAction(event -> {
            // Update the label text
            label.setText("Updated Text");
            sphere.setMaterial(new PhongMaterial(Color.BLUE));
        });

        // Create the scene and set it on the stage
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Update Screen Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
