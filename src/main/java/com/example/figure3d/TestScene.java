package com.example.figure3d;

import com.company.enums.Color;
import com.company.enums.Size;
import com.company.enums.Type;
import com.company.model.Figure;
import com.company.service.Game;
import com.company.service.Param;
import com.company.service.Tools;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.company.service.Game.*;

public class TestScene extends Application {
    public static int points = 0;
    public static int attempts = 3;

    Param condition = null;
    String name_of_game = "";

    Param main_param = Size.SMALL;
    public int ID = 111;

    List<Figure> rightAnswers;
    List<Figure> wrongAnswers;

    //    ArrayList<Figure> threes = (ArrayList<Figure>) Tools.makeThrees_1st(Game.figures, main_param, 3);
    ArrayList<Figure> threes = gameByColor_view(main_param);

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

        Sphere sphere;
        Box box;
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
        Label label_elus = new Label("ЭЛУС");
        label_elus.setLayoutX(600);
        label_elus.setLayoutY(20);
        label_elus.setFont(Font.font("Helvetica", 50));
        label_elus.setTextFill(javafx.scene.paint.Color.RED);
        label_elus.setAlignment(Pos.CENTER);

        Label label_seq = new Label("Текущая последовательность");
        label_seq.setLayoutX(400);
        label_seq.setLayoutY(60);
        label_seq.setFont(Font.font("Helvetica", 40));

        Label label_choose = new Label("Выберете одну фигуру:");
        label_choose.setLayoutX(450);
        label_choose.setLayoutY(250);
        label_choose.setFont(Font.font("Helvetica", 40));

        // Начальные фигуры
        root.getChildren().addAll(label_elus, label_seq, label_choose);
        for (Shape3D shape : shapes) {
            root.getChildren().add(shape);
        }
        root.getChildren().add(light);

        // Фигуры для выбора
        for (Shape3D choice : choices3D) {
            root.getChildren().add(choice);
        }

        // Add 1st button
        Button decision1 = new Button("1");
        decision1.setPrefSize(50, 50);
        decision1.setLayoutX(275);
        decision1.setLayoutY(450);
        decision1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                currentFigure[0] = choices.get(0);

                try {
                    if (playRound(currentFigure[0])) {
                        Tools.draw_figure(choices.get(0), root, shapes);
                        updateFigures();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        root.getChildren().add(decision1);

        // Add 2nd button
        Button decision2 = new Button("2");
        decision2.setPrefSize(50, 50);
        decision2.setLayoutX(385);
        decision2.setLayoutY(450);
        decision2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                currentFigure[0] = choices.get(1);

                try {
                    if (playRound(currentFigure[0])) {
                        Tools.draw_figure(choices.get(1), root, shapes);
                        updateFigures();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        root.getChildren().add(decision2);

        // Add 3rd button
        Button decision3 = new Button("3");
        decision3.setPrefSize(50, 50);
        decision3.setLayoutX(495);
        decision3.setLayoutY(450);
        decision3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                currentFigure[0] = choices.get(2);


                try {
                    if (playRound(currentFigure[0])) {
                        Tools.draw_figure(choices.get(2), root, shapes);
                        updateFigures();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
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
        stage.setTitle("ELUS Game");
        // Display the Stage
        stage.show();
    }

    public void updateFigures() {
        List<List<Figure>> answers = Tools.makeAnswers_1st(main_param, ID);
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

        if (ID == ID_SAME_COLOR || ID == ID_DIFF_COLOR){
            main_param = threes.get(threes.size() - 1).getColor();
        } else if (ID == ID_SAME_SIZE || ID == ID_DIFF_SIZE) {
            main_param = threes.get(threes.size() - 1).getSize();
        } else if (ID == ID_SAME_TYPE || ID == ID_DIFF_TYPE) {
            main_param = threes.get(threes.size() - 1).getType();
        }
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


    public boolean playRound(Figure chosenFigure) throws Exception {

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

        if (conditionFirstRoundNormal) {
            System.out.println("Правильно" + "\n");
            points += 2;
            threes.add(chosenFigure);

            if (ID == ID_SAME_COLOR || ID == ID_DIFF_COLOR){
                main_param = threes.get(threes.size() - 1).getColor();
            } else if (ID == ID_SAME_SIZE || ID == ID_DIFF_SIZE) {
                main_param = threes.get(threes.size() - 1).getSize();
            } else if (ID == ID_SAME_TYPE || ID == ID_DIFF_TYPE) {
                main_param = threes.get(threes.size() - 1).getType();
            }

            if (threes.size() == 8) {
                Tools.dialogWindow(name_of_game, points, "Поздравляем! Вы прошли первый тур!",
                        Alert.AlertType.INFORMATION);
            }

            return true;

        } else if (conditionFirstRoundSpecial) {
            System.out.println("Правильно" + "\n");
            points += 2;
            threes.add(chosenFigure);

            if (threes.size() == 8) {
                Tools.dialogWindow(name_of_game, points, "Поздравляем! Вы прошли первый тур!",
                        Alert.AlertType.INFORMATION);
            }
            return true;

        } else {
            attempts--;
            System.out.println("Неправильно. У вас осталось " + attempts + " попыток" + "\n");

            if (attempts == 0) {
                Tools.dialogWindow(name_of_game, points, "Игра окончена! Вы дисквалифицированы!",
                        Alert.AlertType.ERROR);
            }
            return false;

        }
    }


    public ArrayList<Figure> gameByColor_view(Param currParam) {
        Stream<Figure> currParamAnswers;
        Stream<Figure> wrongParamAnswers;

        //Создание массивов нужного цвета ("правильных") и неправильного ("неверных")
        if (ID == 100) {
            currParamAnswers = figures.stream().filter(figure -> figure.getColor() == currParam);
            wrongParamAnswers = figures.stream().filter(figure -> figure.getColor() != currParam);
        } else if (ID == 101) {
            currParamAnswers = figures.stream().filter(figure -> figure.getSize() == currParam);
            wrongParamAnswers = figures.stream().filter(figure -> figure.getSize() != currParam);
        } else if (ID == 102) {
            currParamAnswers = figures.stream().filter(figure -> figure.getType() == currParam);
            wrongParamAnswers = figures.stream().filter(figure -> figure.getType() != currParam);
        } else {
            currParamAnswers = figures.stream();
            wrongParamAnswers = figures.stream();
        }

        //Перевод из Стримов в Листы
        rightAnswers = currParamAnswers.collect(Collectors.toList());
        wrongAnswers = wrongParamAnswers.collect(Collectors.toList());

        //Режим с иным типом\цветом\размером специфичен и весьма заметно отличается от режима с одинаковым параметром.
        // Так что для создания "правильных" и "неправильных" массивов вызывается дополнительная функция
        if (ID == 110 || ID == 111 || ID == 112) {
            threes = (ArrayList<Figure>) Tools.makeThrees_1st(figures, currParam, 3);

            if (ID == 110) {
                main_param = threes.get(threes.size() - 1).getColor();
                System.out.println("Diff color");
            } else if (ID == 111) {
                main_param = threes.get(threes.size() - 1).getSize();
                System.out.println("Diff size");
            } else if (ID == 112) {
                main_param = threes.get(threes.size() - 1).getType();
                System.out.println("Diff type");
            }

        } else if (ID == 100 || ID == 101 || ID == 102) {
            List<Figure> rightAnswers_copy = new ArrayList<>(rightAnswers.subList(0, rightAnswers.size()));
            threes = (ArrayList<Figure>) Tools.exceptRepeatInList(rightAnswers_copy, 3);
        }

        return threes;
    }
}