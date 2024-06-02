package com.example.figure3d;

import com.company.enums.Size;
import com.company.enums.Type;
import com.company.model.Figure;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.List;

public class DrawUtils {

    // Отрисовка фигур правильной последовательности
    public static ArrayList<Shape3D> getFigure(List<Figure> threes) {

        Sphere sphere;
        Box box;
        ArrayList<Shape3D> figs = new ArrayList<>();

        int x = 150;
        int y = 150;
        int z = 400;

        for (Figure figure : threes) {
            int v;
            int v1;
            int v2;
            PhongMaterial color;

            if (figure.getSize() == Size.BIG) {
                v = 80;
                v1 = 80;
                v2 = 80;
            } else {
                v = 50;
                v1 = 50;
                v2 = 50;
            }

            if (figure.getColor() == com.company.enums.Color.BLUE) {
                color = new PhongMaterial(javafx.scene.paint.Color.BLUE);
            } else {
                color = new PhongMaterial(javafx.scene.paint.Color.YELLOW);
            }

            if (figure.getType() == Type.CIRCLE) {
                sphere = new Sphere(v);
                sphere.setTranslateX(x);
                sphere.setTranslateY(y);
                sphere.setTranslateZ(z);
                sphere.setMaterial(color);
                figs.add(sphere);
            } else {
                box = new Box(v, v1, v2);
                box.setTranslateX(x);
                box.setTranslateY(y);
                box.setTranslateZ(z);
                box.setMaterial(color);
                figs.add(box);
            }
            x += 150;
        }

        return figs;

    }

    // Отрисовка фигур для выбора игроком
    public static ArrayList<Shape3D> getChoices(List<Figure> choices) {

        Sphere sphere;
        Box box;
        ArrayList<Shape3D> figs = new ArrayList<>();

        int x = 150;
        int y = 400;
        int z = 400;

        for (Figure figure : choices) {
            int v;
            int v1;
            int v2;
            PhongMaterial color;

            if (figure.getSize() == Size.BIG) {
                v = 80;
                v1 = 80;
                v2 = 80;
            } else {
                v = 50;
                v1 = 50;
                v2 = 50;
            }

            if (figure.getColor() == com.company.enums.Color.BLUE) {
                color = new PhongMaterial(javafx.scene.paint.Color.BLUE);
            } else {
                color = new PhongMaterial(javafx.scene.paint.Color.YELLOW);
            }

            if (figure.getType() == Type.CIRCLE) {
                sphere = new Sphere(v);
                sphere.setTranslateX(x);
                sphere.setTranslateY(y);
                sphere.setTranslateZ(z);
                sphere.setMaterial(color);
                figs.add(sphere);
            } else {
                box = new Box(v, v1, v2);
                box.setTranslateX(x);
                box.setTranslateY(y);
                box.setTranslateZ(z);
                box.setMaterial(color);
                figs.add(box);
            }
            x += 150;
        }

        return figs;
    }

    /*
     * Метод для выбора отрисовки фигур в javafx
     *
     * @drawing_figure - сама фигура, которая будет отрисовываться
     * @root - Группа объектов в сцене javafx
     * @shapes - массив, состоящий из уже отрисованных 3D фигур (первые правильные 3 штуки нужные для игрока)
     *
     * */
    public static void draw_figure(Figure drawing_figure, Group root, ArrayList<Shape3D> shapes) {
        PhongMaterial fig_color;
        int v;
        int v1;
        int v2;

        if (drawing_figure.getSize() == Size.BIG) {
            v = 80;
            v1 = 80;
            v2 = 80;
        } else {
            v = 50;
            v1 = 50;
            v2 = 50;
        }

        if (drawing_figure.getColor() == com.company.enums.Color.BLUE) {
            fig_color = new PhongMaterial(javafx.scene.paint.Color.BLUE);
        } else {
            fig_color = new PhongMaterial(javafx.scene.paint.Color.YELLOW);
        }

        // Вычисляем координаты для вставляемой фигуры
        double coord = shapes.get(shapes.size() - 1).getTranslateX();

        if (drawing_figure.getType() == Type.CIRCLE) {

            // Create a Sphere
            Sphere sphere = new Sphere(v);
            sphere.setTranslateX(coord + 150);
            sphere.setTranslateY(150);
            sphere.setTranslateZ(400);
            sphere.setMaterial(fig_color);
            root.getChildren().add(sphere);

        } else {

            // Create a Square
            Box box = new Box(v, v1, v2);
            box.setTranslateX(coord + 150);
            box.setTranslateY(150);
            box.setTranslateZ(400);
            box.setMaterial(fig_color);
            root.getChildren().add(box);

        }
    }
}
