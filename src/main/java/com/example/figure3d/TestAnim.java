package com.example.figure3d;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TestAnim extends Application {

     Group root;
     Scene scene;
     Button button;


    @Override
    public void start(Stage s) {

        root = new Group();
        scene = new Scene(root, 250, 350);
        button = new Button();
        button.setText("create circle");

    root.getChildren().add(button);
    s.setScene(scene);
    s.show();

   button.setOnAction(new EventHandler<ActionEvent>() {

       @Override
       public void handle(ActionEvent e) {

           Sphere sphere = new Sphere(30);
            sphere.setTranslateX(120);
            sphere.setTranslateY(0);
            sphere.setTranslateZ(0);
            sphere.setMaterial(new PhongMaterial(javafx.scene.paint.Color.ORANGE));

       TranslateTransition translate = new TranslateTransition(
               Duration.millis(5000));
       translate.setToX(1);
       translate.setToY(432);


    ParallelTransition transition = new ParallelTransition(sphere,translate);
       transition.setCycleCount(1);
       transition.play();
       root.getChildren().add(sphere);

       }
   });
    }

    public static void main(String args[]) {
        Application.launch(args);

    }

}
