package com.example.figure3d;

import com.company.enums.Color;
import com.company.model.Figure;
import com.company.service.Game;
import com.company.service.Param;
import com.company.service.Tools;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.service.Game.*;

public class SecondTour extends Application {
    Stage primaryStage;

    public static int points = TestScene.getPoints();
    public static int attempts = 3;

    Param condition = null;
    String name_of_game = "";

    // Получаем ГТ игры и параметр, относительно которого будет вестись игра
//    public int ID = Game.initializeID_secondRound();
    public int ID = 201;
    Param main_param = Game.initializeParam_secondRound(ID);

    ArrayList<Figure> threes = gameByParam_2nd(main_param);

    List<List<Figure>> totalAnswers = Tools.makeAnswers_2nd(threes, main_param);
    List<Figure> rightAnswers = totalAnswers.get(0);
    List<Figure> wrongAnswers = totalAnswers.get(1);

    // Получение списка ответов для выбора (в определенном (нехорошем) порядке)
    List<Figure> raw_choices = Tools.getChoices(rightAnswers, wrongAnswers, threes, ID, main_param);

    // Получение списка ответов для выбора (в рандомном порядке)
    List<Figure> choices = Tools.makeRandomOrderList(raw_choices);

    // Массив из отрисованных фигур
    ArrayList<Shape3D> shapes = DrawUtils.getFigure(threes);

    // Массив из отрисованных фигур, предложенных для выбора игроку
    ArrayList<Shape3D> choices3D = DrawUtils.getChoices(choices);

    Group root = new Group();

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

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
                        DrawUtils.draw_figure(choices.get(0), root, shapes);
                        updateFigures_2nd();
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
                        DrawUtils.draw_figure(choices.get(1), root, shapes);
                        updateFigures_2nd();
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
                        DrawUtils.draw_figure(choices.get(2), root, shapes);
                        updateFigures_2nd();
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

        stage.setScene(scene);
        stage.setTitle("ELUS Game");
        stage.show();
    }

    public ArrayList<Figure> gameByParam_2nd(Param currParam) {
        Random rand = new Random();
        Figure firstFigure = figures.get(rand.nextInt(0, figures.size()));
        Figure secondFigure = null;
        Figure thirdFigure = null;

        if (ID == 200) {

            // Проверяем первую фигуру и на ее основе формируем вторую
            if (firstFigure.getSize() == currParam) {
                secondFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
            } else {
                secondFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
            }

            // По аналогии формируем третью фигуру
            if (secondFigure.getSize() == currParam) {
                thirdFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
            } else {
                thirdFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
            }

        } else if (ID == 201) {

            // Проверяем первую фигуру и на ее основе формируем вторую
            if (firstFigure.getSize() != currParam) {
                secondFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
            } else {
                secondFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
            }

            // По аналогии формируем третью фигуру
            if (secondFigure.getSize() != currParam) {
                thirdFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
            } else {
                thirdFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
            }

        } else if (ID == 202) {

            // Проверяем первую фигуру и на ее основе формируем вторую
            if (firstFigure.getColor() == currParam) {
                secondFigure = Game.figures_only_circles.get(rand.nextInt(0, Game.figures_only_circles.size()));
            } else {
                secondFigure = Game.figures_only_squares.get(rand.nextInt(0, Game.figures_only_squares.size()));
            }

            // По аналогии формируем третью фигуру
            if (secondFigure.getSize() == currParam) {
                thirdFigure = Game.figures_only_circles.get(rand.nextInt(0, Game.figures_only_circles.size()));
            } else {
                thirdFigure = Game.figures_only_squares.get(rand.nextInt(0, Game.figures_only_squares.size()));
            }

        } else if (ID == 203) {

            // Проверяем первую фигуру и на ее основе формируем вторую
            if (firstFigure.getType() == currParam) {
                secondFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
            } else {
                secondFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
            }

            // По аналогии формируем третью фигуру
            if (secondFigure.getSize() == currParam) {
                thirdFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
            } else {
                thirdFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
            }

        }

        ArrayList<Figure> threes = new ArrayList<>();
        threes.add(firstFigure);
        threes.add(secondFigure);
        threes.add(thirdFigure);

        return threes;
    }

    public void updateFigures_2nd() {
        List<List<Figure>> answers = Tools.makeAnswers_2nd(threes, main_param);
        rightAnswers = answers.get(0);
        wrongAnswers = answers.get(1);

        // Получение списка ответов для выбора (в определенном (нехорошем) порядке)
        raw_choices = Tools.getChoices(rightAnswers, wrongAnswers, threes, ID, main_param);

        // Получение списка ответов для выбора (в рандомном порядке)
        choices = Tools.makeRandomOrderList(raw_choices);

        // Обновление правильной последовательности
        shapes = DrawUtils.getFigure(threes);

        // Удаление прошлых фигур
        for (Shape3D choice : choices3D) {
            root.getChildren().remove(choice);
        }

        // Добавление новых фигур
        choices3D = DrawUtils.getChoices(choices);
        for (Shape3D choice : choices3D) {
            root.getChildren().add(choice);
        }

        //TODO: Поменять тут логику (пока хз на какую)
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

    public boolean playRound(Figure chosenFigure) {
        System.out.println("Curr param: " + main_param);

        getCondition(chosenFigure);

        // 2-Й ТУР //
        // Условие, что правильно выбрали, когда Биг = Блу или SMALL = BLUE
        boolean conditionSecondRound_BigBlue =
                condition == Color.BLUE && threes.get(threes.size() - 1).getSize() == main_param
                        // Проверяем, что выбрали BLUE, если последний элемент в списке - BIG
                        || // или
                        condition == Color.YELLOW && threes.get(threes.size() - 1).getSize() != main_param;
        // Выбираем YELLOW, если последний элемент в списке - SMALL (то бишь, != currObj)


//        System.out.println("curr equality: " + (condition != main_param));
//        System.out.println("curr cond: " + condition);
//        System.out.println("CONDITIONS: 1ST = " + conditionFirstRoundNormal + " 2nd = " + conditionFirstRoundSpecial + " 3rd = " + conditionSecondRound_BigBlue);

        if (conditionSecondRound_BigBlue) {
            Notifications.create().title("Правильно").text("Вы заработали 2 очка!").showInformation();
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
                        Alert.AlertType.INFORMATION, primaryStage, true);
            }

            return true;

        } else {
            attempts--;
            Notifications.create().title("Неправильно!").text("У вас осталось " + attempts + " попыток").showWarning();

            if (attempts == 0) {
                Tools.dialogWindow(name_of_game, points, "Игра окончена! Вы дисквалифицированы!",
                        Alert.AlertType.ERROR, primaryStage, true);
            }
            return false;

        }
    }
}
