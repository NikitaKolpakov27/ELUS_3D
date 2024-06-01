package com.company.service;

import com.company.enums.Color;
import com.company.enums.Size;
import com.company.enums.Type;
import com.company.model.Figure;

import java.util.List;
import java.util.Random;

public class Game {

    //ID для Геймов (геймтайпы)
    public static final int ID_SAME_COLOR = 1_0_0;   //  пойти тем же ЦВЕТОМ
    public static final int ID_SAME_SIZE = 1_0_1;    //  РАЗМЕРОМ
    public static final int ID_SAME_TYPE = 1_0_2;    //  ТИПОМ

    public static final int ID_DIFF_COLOR = 1_1_0;   //  пойти ДРУГИМ ЦВЕТОМ
    public static final int ID_DIFF_SIZE = 1_1_1;    //  РАЗМЕРОМ
    public static final int ID_DIFF_TYPE = 1_1_2;    //  ТИПОМ

    public static final int ID_BIG_BLUE = 2_0_0;     //  Если BIG -> BLUE, SMALL -> YELLOW
    public static final int ID_SMALL_BLUE = 2_0_1;   //  Если SMALL -> BLUE, BIG -> YELLOW
    public static final int ID_BLUE_CIRCLE = 2_0_2;  //  Если BLUE -> CIRCLE, YELLOW -> SQUARE
    public static final int ID_SQUARE_BIG = 2_0_3;   //  Если SQUARE -> BIG, CIRCLE -> SMALL

    public static final List<Integer> FIRST_ROUND_GAME_TYPES = List.of(
      ID_SAME_COLOR, ID_SAME_TYPE, ID_SAME_SIZE, ID_DIFF_COLOR, ID_DIFF_SIZE, ID_DIFF_TYPE
    );

    public static final List<Integer> SECOND_ROUND_GAME_TYPES = List.of(
            ID_BIG_BLUE, ID_SMALL_BLUE, ID_BLUE_CIRCLE, ID_SQUARE_BIG
    );


    public static final List<Figure> figures = List.of(
            new Figure(Color.BLUE, Size.BIG, Type.CIRCLE), new Figure(Color.BLUE, Size.BIG, Type.SQUARE),
            new Figure(Color.BLUE, Size.SMALL, Type.CIRCLE), new Figure(Color.BLUE, Size.SMALL, Type.SQUARE),
            new Figure(Color.YELLOW, Size.BIG, Type.CIRCLE), new Figure(Color.YELLOW, Size.BIG, Type.SQUARE),
            new Figure(Color.YELLOW, Size.SMALL, Type.CIRCLE), new Figure(Color.YELLOW, Size.SMALL, Type.SQUARE));

    public static final List<Figure> figures_only_big = List.of(
            new Figure(Color.BLUE, Size.BIG, Type.CIRCLE), new Figure(Color.BLUE, Size.BIG, Type.SQUARE),
            new Figure(Color.YELLOW, Size.BIG, Type.CIRCLE), new Figure(Color.YELLOW, Size.BIG, Type.SQUARE));

    public static final List<Figure> figures_only_small = List.of(
            new Figure(Color.BLUE, Size.SMALL, Type.CIRCLE), new Figure(Color.BLUE, Size.SMALL, Type.SQUARE),
            new Figure(Color.YELLOW, Size.SMALL, Type.CIRCLE), new Figure(Color.YELLOW, Size.SMALL, Type.SQUARE));

    public static final List<Figure> figures_only_blue = List.of(
            new Figure(Color.BLUE, Size.BIG, Type.CIRCLE), new Figure(Color.BLUE, Size.BIG, Type.SQUARE),
            new Figure(Color.BLUE, Size.SMALL, Type.CIRCLE), new Figure(Color.BLUE, Size.SMALL, Type.SQUARE));

    public static final List<Figure> figures_only_yellow = List.of(
            new Figure(Color.YELLOW, Size.BIG, Type.CIRCLE), new Figure(Color.YELLOW, Size.BIG, Type.SQUARE),
            new Figure(Color.YELLOW, Size.SMALL, Type.CIRCLE), new Figure(Color.YELLOW, Size.SMALL, Type.SQUARE));

    public static final List<Figure> figures_only_circles = List.of(
            new Figure(Color.YELLOW, Size.BIG, Type.CIRCLE), new Figure(Color.BLUE, Size.BIG, Type.CIRCLE),
            new Figure(Color.YELLOW, Size.SMALL, Type.CIRCLE), new Figure(Color.BLUE, Size.SMALL, Type.CIRCLE));

    public static final List<Figure> figures_only_squares = List.of(
            new Figure(Color.BLUE, Size.SMALL, Type.SQUARE), new Figure(Color.YELLOW, Size.BIG, Type.SQUARE),
            new Figure(Color.BLUE, Size.BIG, Type.SQUARE), new Figure(Color.YELLOW, Size.SMALL, Type.SQUARE));

    public Tour tour;

    public Game(Tour tour) {
        this.tour = tour;
    }

    public Tour getTour() {
        return tour;
    }

    public static int initializeID_firstRound() {
        Random randomizer = new Random();
        return FIRST_ROUND_GAME_TYPES.get(randomizer.nextInt(FIRST_ROUND_GAME_TYPES.size()));
    }

    public static int initializeID_secondRound() {
        Random randomizer = new Random();
        return SECOND_ROUND_GAME_TYPES.get(randomizer.nextInt(SECOND_ROUND_GAME_TYPES.size()));
    }

    public static Param initializeParam_firstRound(int ID) {
        Random randomizer = new Random();
        Param selectedParam = null;

        switch (ID) {

            case ID_SAME_COLOR -> {
                int color = randomizer.nextInt(2);

                if (color == 0) {
                    selectedParam = Color.BLUE;
                } else {
                    selectedParam = Color.YELLOW;
                }
            }

            case ID_SAME_SIZE -> {
                int size = randomizer.nextInt(2);

                if (size == 0) {
                    selectedParam = Size.BIG;
                } else {
                    selectedParam = Size.SMALL;
                }
            }

            case ID_SAME_TYPE -> {
                int type = randomizer.nextInt(2);

                if (type == 0) {
                    selectedParam = Type.CIRCLE;
                } else {
                    selectedParam = Type.SQUARE;
                }
            }

            default -> {
                int param_select = randomizer.nextInt(2);

                if (ID == 110) {
                    if (param_select == 0) {
                        selectedParam = Color.BLUE;
                    } else {
                        selectedParam = Color.YELLOW;
                    }
                }

                if (ID == 111) {
                    if (param_select == 0) {
                        selectedParam = Size.BIG;
                    } else {
                        selectedParam = Size.SMALL;
                    }
                }

                if (ID == 112) {
                    if (param_select == 0) {
                        selectedParam = Type.CIRCLE;
                    } else {
                        selectedParam = Type.SQUARE;
                    }
                }
            }
        }
        return selectedParam;
    }

    public static Param initializeParam_secondRound(int ID) {
        Random randomizer = new Random();
        Param selectedParam = null;

        switch (ID) {

            case ID_BIG_BLUE -> selectedParam = Size.BIG;

            case ID_SMALL_BLUE -> selectedParam = Size.SMALL;

            case ID_BLUE_CIRCLE -> {
                int color = randomizer.nextInt(2);

                if (color == 0) {
                    selectedParam = Color.BLUE;
                } else {
                    selectedParam = Color.YELLOW;
                }
            }

            case ID_SQUARE_BIG -> {
                int type = randomizer.nextInt(2);

                if (type == 0) {
                    selectedParam = Type.SQUARE;
                } else {
                    selectedParam = Type.CIRCLE;
                }
            }
        }
        return selectedParam;
    }
}
