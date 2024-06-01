package com.example.figure3d;

import com.company.model.Figure;
import com.company.service.Tools;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SecondTour extends Application {

    Group root = new Group();

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Текущая фигура (выбранная игроком)
        final Figure[] currentFigure = {null};

        // Создаем свет в сцене
        PointLight light = new PointLight();
        light.setTranslateX(350);
        light.setTranslateY(100);
        light.setTranslateZ(300);

        // Create a Camera to view the 3D Shapes
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(100);
        camera.setTranslateY(-50);
        camera.setTranslateZ(300);

        // Add text
        Label label_elus = new Label("ЭЛУС");
        label_elus.setLayoutX(600);
        label_elus.setLayoutY(20);
        label_elus.setFont(Font.font("Helvetica", 50));
        label_elus.setTextFill(javafx.scene.paint.Color.RED);
        label_elus.setAlignment(Pos.CENTER);

        Label label_seq = new Label("Текущая последовательность (2-й тур)");
        label_seq.setLayoutX(400);
        label_seq.setLayoutY(60);
        label_seq.setFont(Font.font("Helvetica", 40));

        Label label_choose = new Label("Выберете одну фигуру:");
        label_choose.setLayoutX(450);
        label_choose.setLayoutY(250);
        label_choose.setFont(Font.font("Helvetica", 40));

        // Начальные фигуры
        root.getChildren().addAll(label_elus, label_seq, label_choose);

        // Create a Scene with depth buffer enabled
        Scene scene = new Scene(root, 1200, 720, true);
        // Add the Camera to the Scene
        scene.setCamera(camera);

        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("ELUS Game");
        // Display the Stage
        stage.show();
    }
}
