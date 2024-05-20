package com.example.figure3d;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestLooping extends Application {

    private boolean isButtonPressed = true; // Flag to track button state
    private int attempts = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Button Loop Example");

        Button start = new Button("Start Loop");
        Button startButton = new Button("Increment");
        Button stopButton = new Button("Stop");

        // Create a VBox layout to hold the buttons
        VBox vbox = new VBox(10, start, startButton, stopButton);

        // Create a Timeline animation with a KeyFrame that repeats
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000), event -> {
                    if (isButtonPressed) {
                        System.out.println(attempts);
                        if (attempts == 4) {
                            System.out.println("Поздравляем!");
                            isButtonPressed = false;
                        }
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely


        // Event handler for the start button
        startButton.setOnAction(event -> {
            attempts++;
        });

        // Event handler for the start button
        start.setOnAction(event -> {
            timeline.play(); // Start the loop
        });

        // Event handler for the stop button
        stopButton.setOnAction(event -> {
            attempts = 0;
            timeline.stop(); // Stop the loop
        });

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
