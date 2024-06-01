package com.company.service;

import com.company.enums.Color;
import com.company.enums.Size;
import com.company.enums.Type;
import com.company.model.Figure;
import com.example.figure3d.SecondTour;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.company.service.Game.figures;

public class Tools {

    static Predicate<Figure> diffColor;
    static Predicate<Figure> sameColor;

    static Predicate<Figure> diffSize;
    static Predicate<Figure> sameSize;

    static Predicate<Figure> diffType;
    static Predicate<Figure> sameType;

    public static void initializePredicates(Object currObj) {
        diffColor = figure -> figure.getColor() != currObj;
        sameColor = figure -> figure.getColor() == currObj;

        diffSize = figure -> figure.getSize() != currObj;
        sameSize = figure -> figure.getSize() == currObj;

        diffType = figure -> figure.getType() != currObj;
        sameType = figure -> figure.getType() == currObj;
    }

    public static List<Figure> makeThrees_1st(List<Figure> list, Object currObj, int neddedElems) {
        List<Figure> copy_list = list;
        List<Figure> newList;

        initializePredicates(currObj);

        if (currObj instanceof Color) {
            newList = filterListByPredicates(diffColor, sameColor, copy_list, neddedElems);
        } else if (currObj instanceof Size) {
            newList = filterListByPredicates(diffSize, sameSize, copy_list, neddedElems);
        } else {
            newList = filterListByPredicates(diffType, sameType, copy_list, neddedElems);
        }

        return newList;
    }


    /*
    *
    *
    * */
    private static List<Figure> filterListByPredicates(Predicate<Figure> predicate_even,
                                Predicate<Figure> predicate_odd, List<Figure> copy_list, int neddedElems) {
        Random rand = new Random();
        List<Figure> newList = new ArrayList<>();
        Stream<Figure> tempStream;

        for (int count = 0; count < neddedElems; count++) {

            if (count % 2 == 0) {
                tempStream = copy_list.stream().filter(predicate_even);
            } else {
                tempStream = copy_list.stream().filter(predicate_odd);
            }

            List<Figure> tempList = tempStream.toList();
            int randomIndex = rand.nextInt(tempList.size());
            Figure neddedFig = tempList.get(randomIndex);
            newList.add(neddedFig);

        }
        return newList;
    }

    public static List<Figure> exceptRepeatInList(List<Figure> list, int elems) {
        Random rand = new Random();
        List<Figure> copy_list = list;
        List<Figure> newList = new ArrayList<>();

        for (int i = 0; i < elems; i++) {
            int randomIndex = rand.nextInt(copy_list.size());
            Figure randomElement = copy_list.get(randomIndex);
            newList.add(randomElement);
            copy_list.remove(randomIndex);
        }
        return newList;
    }

    public static List<Figure> makeRandomOrderList(List<Figure> list) {
        Random rand = new Random();

        List<Figure> choices_copy = new ArrayList<>(list.subList(0, list.size()));
        List<Figure> tempList = new ArrayList<>();

        //Создание рандомного порядка возможных ответов
        int numberOfElements = 3;
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(choices_copy.size());
            Figure randomElement = choices_copy.get(randomIndex);
            tempList.add(randomElement);
            choices_copy.remove(randomIndex);
        }

        return tempList;
    }

    /*
    * Возвращает список из двух списков: 1 = правильные ответы, 2 = неправильные
    *
    * @threes - список из 3-х фигур, предложенных для выбора пользователю (1 прав., 2 неправ.)
    * @rightParam - параметр, относительно которого идет составление правильных и неправильных ответов
    *
    * @return - возвращает два списка, правильные и неправильные ответы
    *
    * */
    public static List<List<Figure>> makeAnswers_2nd(List<Figure> threes, Param rightParam) {
        Figure lastFigure = threes.get(threes.size() - 1);
        List<Figure> rightAnswers;
        List<Figure> wrongAnswers;

        // Проверка rightParam на параметр - ЦВЕТ, ТИП или РАЗМЕР

        // Например: если размер BIG(или SMALL) -> цвет = BLUE
        if (rightParam instanceof Size) {

            if (lastFigure.getSize() == rightParam) {
                rightAnswers = Game.figures_only_blue;
                wrongAnswers = Game.figures_only_yellow;
            } else {
                rightAnswers = Game.figures_only_yellow;
                wrongAnswers = Game.figures_only_blue;
            }

        // Например: если цвет BLUE -> тип = CIRCLE
        } else if (rightParam instanceof Color) {

            if (lastFigure.getColor() == rightParam) {
                rightAnswers = Game.figures_only_circles;
                wrongAnswers = Game.figures_only_squares;
            } else {
                rightAnswers = Game.figures_only_squares;
                wrongAnswers = Game.figures_only_circles;
            }

        // Например: если тип SQUARE -> размер = BIG
        } else {

            if (lastFigure.getType() == rightParam) {
                rightAnswers = Game.figures_only_big;
                wrongAnswers = Game.figures_only_small;
            } else {
                rightAnswers = Game.figures_only_small;
                wrongAnswers = Game.figures_only_big;
            }
        }

        List<List<Figure>> answers = new ArrayList<>();
        answers.add(rightAnswers);
        answers.add(wrongAnswers);

        return answers;
    }

    // 2nd Tour
    public static List<Figure> makeThrees_2nd(Param param) {
        Random rand = new Random();
        Figure firstFigure = figures.get(rand.nextInt(0, figures.size()));
        Figure secondFigure;
        Figure thirdFigure;

        // Проверяем первую фигуру и на ее основе формируем вторую
        if (firstFigure.getSize() == param) {
            secondFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
        } else {
            secondFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
        }

        // По аналогии формируем третью фигуру
        if (secondFigure.getSize() == param) {
            thirdFigure = Game.figures_only_blue.get(rand.nextInt(0, Game.figures_only_blue.size()));
        } else {
            thirdFigure = Game.figures_only_yellow.get(rand.nextInt(0, Game.figures_only_yellow.size()));
        }

        List<Figure> threes = new ArrayList<>();
        threes.add(firstFigure);
        threes.add(secondFigure);
        threes.add(thirdFigure);

        return threes;
    }

    /*
    * Метод для выбора правильного и неправильных ответов
    *
    * @rightAnswers - правильные ответы (правильные фигуры)
    * @wrongAnswers - неправильные ответы (неправильные фигуры)
    * @threes - массив, состоящий из 3-х фигур, предложенных для выбора игроку (2 неправильных и 1 правильный)
    * @id идентификатор игры (111, 110 и 112 - игры, где надо пойти другим цветом, размером или типом)
    * @currObj - объект, показывающий, относительно чего будет вестись проверка (цвет, размер, тип)
    *
    *
    * */
    public static List<Figure> getChoices(List<Figure> rightAnswers, List<Figure> wrongAnswers,
                                    List<Figure> threes, int id, Object currObj) {
        Random rand = new Random();

        if (id == 111 || id == 110 || id == 112) {
            initializePredicates(currObj);
            Stream<Figure> wrongAnswers_stream;
            Stream<Figure> rightAnswers_stream;

            if (currObj instanceof Color) {
                if (threes.get(threes.size() - 1).getColor() == currObj) {
                    wrongAnswers_stream = figures.stream().filter(Tools.sameColor);
                    rightAnswers_stream = figures.stream().filter(Tools.diffColor);
                } else {
                    wrongAnswers_stream = figures.stream().filter(Tools.diffColor);
                    rightAnswers_stream = figures.stream().filter(Tools.sameColor);
                }
            } else if (currObj instanceof Size) {
                if (threes.get(threes.size() - 1).getSize() == currObj) {
                    wrongAnswers_stream = figures.stream().filter(Tools.sameSize);
                    rightAnswers_stream = figures.stream().filter(Tools.diffSize);
                } else {
                    wrongAnswers_stream = figures.stream().filter(Tools.diffSize);
                    rightAnswers_stream = figures.stream().filter(Tools.sameSize);
                }
            } else  {
                if (threes.get(threes.size() - 1).getType() == currObj) {
                    wrongAnswers_stream = figures.stream().filter(Tools.sameType);
                    rightAnswers_stream = figures.stream().filter(Tools.diffType);
                } else {
                    wrongAnswers_stream = figures.stream().filter(Tools.diffType);
                    rightAnswers_stream = figures.stream().filter(Tools.sameType);
                }
            }

            rightAnswers = rightAnswers_stream.collect(Collectors.toList());
            wrongAnswers = wrongAnswers_stream.collect(Collectors.toList());
        }

        int ran = rand.nextInt(rightAnswers.size()); //Рандомный индекс по "правильному" листу

        int ran_wr_1 = rand.nextInt(wrongAnswers.size()); //Рандомный индекс_1 по "неправильному" листу
        int ran_wr_2 = rand.nextInt(wrongAnswers.size()); //Рандомный индекс_2 по "неправильному" листу

        //Создание выбора (состоит из 1 правильного и 2-х неправильных вариантов)
        return List.of(rightAnswers.get(ran), wrongAnswers.get(ran_wr_1), wrongAnswers.get(ran_wr_2));

    }

    /*
    * Метод для выбора отрисовки фигур в javafx
    *
    * @drawing_figure - сама фигура, которая будет отрисовываться
    * @root - Группа объектов в сцене javafx
    * @shapes - массив, состоящий из 3D фигур, которые уже отрисованы (первые правильные 3 штуки нужные для игрока)
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

    public static List<List<Figure>> makeAnswers_1st(Param currParam, int id) {
        Stream<Figure> currParamAnswers = null;
        Stream<Figure> wrongParamAnswers = null;

        //Создание массивов нужного цвета ("правильных") и неправильного ("неверных")
        if (id == 100) {
            currParamAnswers = figures.stream().filter(figure -> figure.getColor() == currParam);
            wrongParamAnswers = figures.stream().filter(figure -> figure.getColor() != currParam);
        } else if (id == 101) {
            currParamAnswers = figures.stream().filter(figure -> figure.getSize() == currParam);
            wrongParamAnswers = figures.stream().filter(figure -> figure.getSize() != currParam);
        } else if (id == 102) {
            currParamAnswers = figures.stream().filter(figure -> figure.getType() == currParam);
            wrongParamAnswers = figures.stream().filter(figure -> figure.getType() != currParam);
        } else if (id == 110 || id == 111 || id == 112) {
            currParamAnswers = figures.stream();
            wrongParamAnswers = figures.stream();
        }

        //Перевод из Стримов в Листы
        List<Figure> rightAnswers = currParamAnswers.toList();
        List<Figure> wrongAnswers = wrongParamAnswers.toList();

        List<List<Figure>> answers = List.of(rightAnswers, wrongAnswers);
        return answers;
    }

    // Диалоговое окно на выходе из приложения (выигрыш/проигрыш)
    public static void dialogWindow(String name_of_game, int points, String text, Alert.AlertType alertType, Stage stage) {

        Alert alert = new Alert(alertType);
        alert.setTitle("ELUS");
        alert.setHeaderText(text + " Условием было: " + name_of_game);
        alert.setContentText("Набрано очков: " + points);

        Optional<ButtonType> result = alert.showAndWait();

        // Если мы не прошли 1-й тур, то игра заканчивается. Если прошли - начинается 2-ой тур
        if (alert.getAlertType() == Alert.AlertType.INFORMATION) {
            if (result.get() == ButtonType.OK || result.get() == ButtonType.CLOSE) {
//            Platform.exit();
                stage.close();
                new SecondTour().start(new Stage());
            }
        } else {
            stage.close();
        }
    }
}
