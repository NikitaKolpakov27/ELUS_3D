package com.company.service;

import com.company.enums.Color;
import com.company.enums.Size;
import com.company.enums.Type;
import com.company.model.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.company.service.Game.figures;

public class FirstTour implements Tour {

    public GameProcess gameProcess;

    public FirstTour(GameProcess gameProcess) {
        this.gameProcess = gameProcess;
    }


    private void gameByColor(Color currColor) {
        Random rand = new Random();

        // Создание "булевой" переменной для понятия, в какой режим играть (идентичный цвет\размер\тип или иной)
        int bool = rand.nextInt(2);


        Stream<Figure> currColorAnswers;
        Stream<Figure> wrongColorAnswers;
        if (bool == 0) {
            //Создание массивов нужного цвета ("правильных") и неправильного ("неверных")
            currColorAnswers = figures.stream().filter(figure -> figure.getColor() == currColor);
            wrongColorAnswers = figures.stream().filter(figure -> figure.getColor() != currColor);
        } else {
            currColorAnswers = figures.stream();
            wrongColorAnswers = figures.stream();
        }

        //Перевод из Стримов в Листы
        List<Figure> rightAnswers = currColorAnswers.collect(Collectors.toList());
        List<Figure> wrongAnswers = wrongColorAnswers.collect(Collectors.toList());

        List<Figure> threes;

        //Режим с иным типом\цветом\размером специфичен и весьма заметно отличается от режима с одинаковым параметром.
        // Так что для создания "правильных" и "неправильных" массивов вызывается дополнительная функция
        if (bool == 1) {
            threes = Tools.makeThrees_1st(figures, currColor, 3);
            System.out.println("Diff color");
            this.gameProcess.playRound(currColor, rightAnswers, wrongAnswers, threes, bool);
        } else {
            List<Figure> rightAnswers_copy = new ArrayList<>(rightAnswers.subList(0, rightAnswers.size()));
            threes = Tools.exceptRepeatInList(rightAnswers_copy, 3);
            this.gameProcess.playRound(currColor, rightAnswers, wrongAnswers, threes, bool);
        }
    }

    private void gameBySize(Size currSize) {
        Random rand = new Random();

        int bool = rand.nextInt(2);


        Stream<Figure> currSizeAnswers;
        Stream<Figure> wrongSizeAnswers;
        if (bool == 0) {
            //Создание массивов нужного цвета ("правильных") и неправильного ("неверных")
            currSizeAnswers = figures.stream().filter(figure -> figure.getSize() == currSize);
            wrongSizeAnswers = figures.stream().filter(figure -> figure.getSize() != currSize);
        } else {
            currSizeAnswers = figures.stream();
            wrongSizeAnswers = figures.stream();
        }

        //Перевод из Стримов в Листы
        List<Figure> rightAnswers = currSizeAnswers.collect(Collectors.toList());
        List<Figure> wrongAnswers = wrongSizeAnswers.collect(Collectors.toList());

        List<Figure> threes;

        //Режим с иным типом\цветом\размером специфичен и весьма заметно отличается от режима с одинаковым параметром.
        // Так что для создания "правильных" и "неправильных" массивов вызывается дополнительная функция
        if (bool == 1) {
//            rightAnswers = Tools.filterDifferentParamsList(figures, currSize, 8);

            threes = Tools.makeThrees_1st(figures, currSize, 3);

            System.out.println("Diff size");

            this.gameProcess.playRound(currSize, rightAnswers, wrongAnswers, threes, bool);
        } else {
            List<Figure> rightAnswers_copy = new ArrayList<>(rightAnswers.subList(0, rightAnswers.size()));

            threes = Tools.exceptRepeatInList(rightAnswers_copy, 3);

            this.gameProcess.playRound(currSize, rightAnswers, wrongAnswers, threes, bool);
        }
    }

    private void gameByType(Type currType) {
        Random rand = new Random();

        int bool = rand.nextInt(2);


        Stream<Figure> currTypeAnswers;
        Stream<Figure> wrongTypeAnswers;
        if (bool == 0) {
            //Создание массивов нужного цвета ("правильных") и неправильного ("неверных")
            currTypeAnswers = figures.stream().filter(figure -> figure.getType() == currType);
            wrongTypeAnswers = figures.stream().filter(figure -> figure.getType() != currType);
        } else {
            currTypeAnswers = figures.stream();
            wrongTypeAnswers = figures.stream();
        }

        //Перевод из Стримов в Листы
        List<Figure> rightAnswers = currTypeAnswers.collect(Collectors.toList());
        List<Figure> wrongAnswers = wrongTypeAnswers.collect(Collectors.toList());

        //Лист из начальных 3-х элементов (правильных)
        List<Figure> threes;

        //Режим с иным типом\цветом\размером специфичен и весьма заметно отличается от режима с одинаковым параметром.
        // Так что для создания "правильных" и "неправильных" массивов вызывается дополнительная функция
        if (bool == 1) {
//            rightAnswers = Tools.filterDifferentParamsList(figures, currType, 8);

            threes = Tools.makeThrees_1st(figures, currType, 3);

            System.out.println("Diff type");

            this.gameProcess.playRound(currType, rightAnswers, wrongAnswers, threes, bool);
        } else {
            //Копирование листа правильных ответов (а то почему-то удаляются элементы после вызова exceptRepeatInList)
            List<Figure> rightAnswers_copy = new ArrayList<>(rightAnswers.subList(0, rightAnswers.size()));

            //Лист из начальных 3-х элементов (правильных)
            threes = Tools.exceptRepeatInList(rightAnswers_copy, 3);

            this.gameProcess.playRound(currType, rightAnswers, wrongAnswers, threes, bool);
        }
    }

    @Override
    public void startTour() {
        //Рандомно получаем, как будем играть (по цвету, размеру или типу)
        Object obj = null;
        Random random = new Random();
        int rand = random.nextInt(3);

        if (rand == 0) {
            obj = Color.BLUE;
        } if (rand == 1) {
            obj = Size.BIG;
        } if (rand == 2) {
            obj = Type.CIRCLE;
        }

        //Выбор правильного цвета для игры
        if (obj instanceof Color) {
            int ran = random.nextInt(2);
            Color currColor;

            if (ran == 0) {
                currColor = Color.BLUE;
                gameByColor(currColor);
            } else {
                currColor = Color.YELLOW;
                gameByColor(currColor);
            }

            //Выбор правильного размера для игры
        } else if (obj instanceof Size) {
            int ran = random.nextInt(2);
            Size currSize;

            if (ran == 0) {
                currSize = Size.BIG;
                gameBySize(currSize);
            } else {
                currSize = Size.SMALL;
                gameBySize(currSize);
            }

            //Выбор правильного типа для игры
        } else {
            int ran = random.nextInt(2);
            Type currType;

            if (ran == 0) {
                currType = Type.CIRCLE;
                gameByType(currType);
            } else {
                currType = Type.SQUARE;
                gameByType(currType);
            }
        }
    }
}
