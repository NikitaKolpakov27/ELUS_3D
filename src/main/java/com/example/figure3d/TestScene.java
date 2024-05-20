package com.example.figure3d;

import com.company.enums.Color;
import com.company.enums.Size;
import com.company.enums.Type;
import com.company.model.Figure;
import com.company.service.Game;
import com.company.service.Param;
import com.company.service.Tools;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.company.service.Game.figures;

public class TestScene extends Application {
    public static int points = 0;
    public static int attempts = 3;

    Param condition = null;
    String name_of_game = "";

    public Param main_param = Color.YELLOW;
    public int ID = 110;

    List<Figure> rightAnswers;
    List<Figure> wrongAnswers;

    //    ArrayList<Figure> threes = (ArrayList<Figure>) Tools.makeThrees_1st(Game.figures, main_param, 3);
    ArrayList<Figure> threes = gameByColor_view((Color) main_param);

    // Сделать нормальные ответы

    // Получение списка ответов для выбора (в определенном (нехорошем) порядке)
    List<Figure> raw_choices = Tools.getChoices(rightAnswers, wrongAnswers, threes, ID, main_param);

    // Получение списка ответов для выбора (в рандомном порядке)
    List<Figure> choices = Tools.makeRandomOrderList(raw_choices);

    
    public ArrayList<Shape3D> getFigure() {

        Sphere sphere = null;
        Box box = null;
        ArrayList<Shape3D> figs = new ArrayList<>();

        int x = 150;
        int y = 150;
        int z = 400;

        for (Figure figure : threes) {
            int v = 0;
            int v1 = 0;
            int v2 = 0;
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

    public ArrayList<Shape3D> getChoices(List<Figure> threes, Param currObj) {

        Sphere sphere = null;
        Box box = null;
        ArrayList<Shape3D> figs = new ArrayList<>();

        int x = 150;
        int y = 400;
        int z = 400;

        for (Figure figure : choices) {
            int v = 0;
            int v1 = 0;
            int v2 = 0;
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

        System.out.println("Right: " + rightAnswers);
        System.out.println("Wrong: " + wrongAnswers);
        System.out.println("Param: " + main_param);
        return figs;

    }

    // Массив из отрисованных фигур
    ArrayList<Shape3D> shapes = getFigure();

    // Массив из отрисованных фигур, предложенных для выбора игроку
    ArrayList<Shape3D> choices3D = getChoices(threes, main_param);

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
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(400);
        textFlow.setLayoutY(20);
        Text text = new Text("Текущая последовательность:");
        text.setFont(Font.font("Helvetica", 50));
        textFlow.getChildren().add(text);

        // Add text
        TextFlow chooseFigures = new TextFlow();
        chooseFigures.setLayoutX(400);
        chooseFigures.setLayoutY(200);
        Text chooseFiguresText = new Text("Выберете одну фигуру:");
        chooseFiguresText.setFont(Font.font("Helvetica", 50));
        chooseFigures.getChildren().add(chooseFiguresText);

        // Начальные фигуры
        root.getChildren().addAll(textFlow, chooseFigures);
        for (Shape3D shape : shapes) {
            root.getChildren().add(shape);
        }
        root.getChildren().add(light);

        // Фигуры для выбора
        for (Shape3D choice : choices3D) {
            root.getChildren().add(choice);
        }

        // Add 1st button
        Button decision1 = new Button("Сделать выбор 1");
        decision1.setPrefSize(100, 100);
        decision1.setLayoutX(300);
        decision1.setLayoutY(500);
        decision1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                currentFigure[0] = choices.get(0);

                if (playRound(currentFigure[0])) {
                    Tools.draw_figure(choices.get(0), root, shapes);
                    updateFigures();
                }
            }
        });
        root.getChildren().add(decision1);

        // Add 2nd button
        Button decision2 = new Button("Сделать выбор 2");
        decision2.setPrefSize(100, 100);
        decision2.setLayoutX(400);
        decision2.setLayoutY(500);
        decision2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                currentFigure[0] = choices.get(1);

                if (playRound(currentFigure[0])) {
                    Tools.draw_figure(choices.get(1), root, shapes);
                    updateFigures();
                }

            }
        });
        root.getChildren().add(decision2);

        // Add 3rd button
        Button decision3 = new Button("Сделать выбор 2");
        decision3.setPrefSize(100, 100);
        decision3.setLayoutX(500);
        decision3.setLayoutY(500);
        decision3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                currentFigure[0] = choices.get(2);


                if (playRound(currentFigure[0])) {
                    Tools.draw_figure(choices.get(2), root, shapes);
                    updateFigures();
                }
            }
        });
        root.getChildren().add(decision3);

        // Create a Scene with depth buffer enabled
        Scene scene = new Scene(root, 1200, 720, true);
        // Add the Camera to the Scene
        scene.setCamera(camera);

        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("An Example with Predefined 3D Shapes");
        // Display the Stage
        stage.show();
    }

    public void updateFigures() {
        List<List<Figure>> answers = Tools.makeAnswers_1st(main_param);
//        List<List<Figure>> answers = Tools.makeAnswers_2nd(threes, main_param);
        rightAnswers = answers.get(0);
        wrongAnswers = answers.get(1);

        // Получение списка ответов для выбора (в определенном (нехорошем) порядке)
        raw_choices = Tools.getChoices(rightAnswers, wrongAnswers, threes, ID, main_param);

        // Получение списка ответов для выбора (в рандомном порядке)
        choices = Tools.makeRandomOrderList(raw_choices);

        // Обновление правильной последовательности
        shapes = getFigure();

        // Удаление прошлых фигур
        for (Shape3D choice : choices3D) {
            root.getChildren().remove(choice);
        }

        // Добавление новых фигур
        choices3D = getChoices(threes, main_param);
        for (Shape3D choice : choices3D) {
            root.getChildren().add(choice);
        }

        main_param = threes.get(threes.size() - 1).getColor();
    }

    private void getCondition(Figure choice) {

        switch (ID) {

            case Game.ID_SAME_COLOR -> {
                condition = choice.getColor();
                name_of_game = "Пойти фигурой того же цвета.";
            }

            case Game.ID_SAME_SIZE -> {
                condition = choice.getSize();
                name_of_game = "Пойти фигурой того же размера.";
            }

            case Game.ID_SAME_TYPE -> {
                condition = choice.getType();
                name_of_game = "Пойти фигурой того же типа.";
            }

            case Game.ID_DIFF_COLOR -> {
                condition = choice.getColor();
                name_of_game = "Пойти фигурой иного цвета.";
            }

            case Game.ID_DIFF_SIZE -> {
                condition = choice.getSize();
                name_of_game = "Пойти фигурой иного размера.";
            }

            case Game.ID_DIFF_TYPE -> {
                condition = choice.getType();
                name_of_game = "Пойти фигурой иного типа.";
            }


            // 2-й тур
            case Game.ID_BIG_BLUE -> {
                condition = choice.getColor();
                name_of_game = "Если последняя фигура BIG, пойти синим, иначе желтым";
            }

            case Game.ID_SMALL_BLUE -> {
                condition = choice.getColor();
                name_of_game = "Если последняя фигура SMALL, пойти синим, иначе желтым";
            }

            case Game.ID_BLUE_CIRCLE -> {
                condition = choice.getType();
                name_of_game = "Если последняя фигура BLUE, пойти кругом, иначе квадратом";
            }

            case Game.ID_SQUARE_BIG -> {
                condition = choice.getSize();
                name_of_game = "Если последняя фигура SQUARE, пойти большим, иначе малым";
            }
        }
    }


    public boolean playRound(Figure chosenFigure) {

        getCondition(chosenFigure);

        // 1-Й ТУР //
        // Условие, что правильно выбрали в первых 3-х геймах
        boolean conditionFirstRoundNormal = condition == main_param &&
                (ID == Game.ID_SAME_SIZE || ID == Game.ID_SAME_COLOR || ID == Game.ID_SAME_TYPE);

        // Условие, что правильно выбрали в последних 3-х геймах
        boolean conditionFirstRoundSpecial = condition != main_param &&
                (ID == Game.ID_DIFF_SIZE || ID == Game.ID_DIFF_COLOR || ID == Game.ID_DIFF_TYPE);

        // 2-Й ТУР //
        // Условие, что правильно выбрали, когда Биг = Блу или SMALL = BLUE
        boolean conditionSecondRound_BigBlue =
                condition == Color.BLUE && threes.get(threes.size() - 1).getSize() == main_param
                        // Проверяем, что выбрали BLUE, если последний элемент в списке - BIG
                        || // или
                condition == Color.YELLOW && threes.get(threes.size() - 1).getSize() != main_param;
                        // Выбираем YELLOW, если последний элемент в списке - SMALL (то бишь, != currObj)


        System.out.println("curr equality: " + (condition != main_param));
        System.out.println("curr cond: " + condition);
        System.out.println("CONDITIONS: 1ST = " + conditionFirstRoundNormal + " 2nd = " + conditionFirstRoundSpecial + " 3rd = " + conditionSecondRound_BigBlue);
//        if (conditionFirstRoundNormal | conditionFirstRoundSpecial | conditionSecondRound_BigBlue) {
        if (conditionFirstRoundNormal) {
            System.out.println("Правильно" + "\n");
            points += 2;
            threes.add(chosenFigure);
            main_param = threes.get(threes.size() - 1).getColor();

            if (threes.size() == 8) {
                System.out.println("Поздравляем! Вы прошли первый тур! Условием было: " + name_of_game);
                System.out.println("Набрано очков: " + points);
            }

            return true;

        } else if (conditionFirstRoundSpecial) {
            System.out.println("Правильно" + "\n");
            points += 2;
            threes.add(chosenFigure);

            if (threes.size() == 8) {
                System.out.println("Поздравляем! Вы прошли первый тур! Условием было: " + name_of_game);
                System.out.println("Набрано очков: " + points);
            }

            return true;
        } else {
            attempts--;
            System.out.println("Неправильно. У вас осталось " + attempts + " попыток" + "\n");
            points -= 1;

            if (attempts == 0) {
                System.out.println("Игра окончена. Вы дисквалифицированы.");
                System.out.println("Условием было: " + name_of_game);
                System.out.println("Набрано очков: " + Math.round(points));
            }

            return false;
        }
    }


    public ArrayList<Figure> gameByColor_view(Color currColor) {
        Stream<Figure> currColorAnswers;
        Stream<Figure> wrongColorAnswers;

        if (ID == 100) {
            //Создание массивов нужного цвета ("правильных") и неправильного ("неверных")
            currColorAnswers = figures.stream().filter(figure -> figure.getColor() == currColor);
            wrongColorAnswers = figures.stream().filter(figure -> figure.getColor() != currColor);
        } else {
            currColorAnswers = figures.stream();
            wrongColorAnswers = figures.stream();
        }

        //Перевод из Стримов в Листы
        rightAnswers = currColorAnswers.collect(Collectors.toList());
        wrongAnswers = wrongColorAnswers.collect(Collectors.toList());

        //Режим с иным типом\цветом\размером специфичен и весьма заметно отличается от режима с одинаковым параметром.
        // Так что для создания "правильных" и "неправильных" массивов вызывается дополнительная функция
        if (ID == 110) {
            threes = (ArrayList<Figure>) Tools.makeThrees_1st(figures, currColor, 3);
            main_param = threes.get(threes.size() - 1).getColor();
            System.out.println("Diff color");
        } else if (ID == 100) {
            List<Figure> rightAnswers_copy = new ArrayList<>(rightAnswers.subList(0, rightAnswers.size()));
            threes = (ArrayList<Figure>) Tools.exceptRepeatInList(rightAnswers_copy, 3);
        }

        return threes;
    }
}