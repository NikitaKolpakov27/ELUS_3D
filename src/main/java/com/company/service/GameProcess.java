package com.company.service;

import com.company.enums.Color;
import com.company.model.Figure;

import java.util.List;
import java.util.Scanner;

public class GameProcess {
    private int attempts = 3; // Количество попыток
    private int points; // Очки игрока

    //Игровой процесс. Вынесен в отдельный метод для оптимизации
    public void playRound(Param currObj, List<Figure> rightAnswers,
                                       List<Figure> wrongAnswers, List<Figure> threes, int id) {

        //Пока есть попытки, можно играть
        int rounds = 1;
        while (attempts > 0) {
            List<List<Figure>> answers = Tools.makeAnswers_2nd(threes, currObj);
            rightAnswers = answers.get(0);
            wrongAnswers = answers.get(1);

            System.out.println("Текущая последовательность: " + threes);
            System.out.println("\n");

            List<Figure> choices = Tools.getChoices(rightAnswers, wrongAnswers, threes, id, currObj);

            // Получение списка ответов для выбора (в рандомном порядке)
            choices = Tools.makeRandomOrderList(choices);

            System.out.println("Choices: " + choices);
            System.out.println("Выберете правильный ответ(цифра): ");

            Scanner scn = new Scanner(System.in);
            int ans = scn.nextInt();

            // Процесс выбора
            // В зависимости от переданного объекта, выбирается, какой метод будет вызываться
            Object condition = null;
            String name_of_game = "";

            switch (id) {

                case Game.ID_SAME_COLOR -> {
                    condition = choices.get(ans - 1).getColor();
                    name_of_game = "Пойти фигурой того же цвета.";
                }

                case Game.ID_SAME_SIZE -> {
                    condition = choices.get(ans - 1).getSize();
                    name_of_game = "Пойти фигурой того же размера.";
                }

                case Game.ID_SAME_TYPE -> {
                    condition = choices.get(ans - 1).getType();
                    name_of_game = "Пойти фигурой того же типа.";
                }

                case Game.ID_DIFF_COLOR -> {
                    condition = choices.get(ans - 1).getColor();
                    name_of_game = "Пойти фигурой иного цвета.";
                }

                case Game.ID_DIFF_SIZE -> {
                    condition = choices.get(ans - 1).getSize();
                    name_of_game = "Пойти фигурой иного размера.";
                }

                case Game.ID_DIFF_TYPE -> {
                    condition = choices.get(ans - 1).getType();
                    name_of_game = "Пойти фигурой иного типа.";
                }

                // 2-й тур
                case Game.ID_BIG_BLUE -> {
                    condition = choices.get(ans - 1).getColor();
                    name_of_game = "Если последняя фигура BIG, пойти синим, иначе желтым";
                }

                case Game.ID_SMALL_BLUE -> {
                    condition = choices.get(ans - 1).getColor();
                    name_of_game = "Если последняя фигура SMALL, пойти синим, иначе желтым";
                }

                case Game.ID_BLUE_CIRCLE -> {
                    condition = choices.get(ans - 1).getType();
                    name_of_game = "Если последняя фигура BLUE, пойти кругом, иначе квадратом";
                }

                case Game.ID_SQUARE_BIG -> {
                    condition = choices.get(ans - 1).getSize();
                    name_of_game = "Если последняя фигура SQUARE, пойти большим, иначе малым";
                }

            }

            // 1-Й ТУР //
            // Условие, что правильно выбрали в первых 3-х геймах
            boolean conditionFirstRoundNormal = condition == currObj &&
                    id == Game.ID_SAME_SIZE || id == Game.ID_SAME_COLOR || id == Game.ID_SAME_TYPE;

            // Условие, что правильно выбрали в последних 3-х геймах
            boolean conditionFirstRoundSpecial = condition != currObj &&
                    id == Game.ID_DIFF_SIZE || id == Game.ID_DIFF_COLOR || id == Game.ID_DIFF_TYPE;

            // 2-Й ТУР //
            // Условие, что правильно выбрали, когда Биг = Блу или SMALL = BLUE
            boolean conditionSecondRound_BigBlue =
                    condition == Color.BLUE && threes.get(threes.size() - 1).getSize() == currObj
                            // Проверяем, что выбрали BLUE, если последний элемент в списке - BIG
                            || // или
                    condition == Color.YELLOW && threes.get(threes.size() - 1).getSize() != currObj;
                            // Выбираем YELLOW, если последний элемент в списке - SMALL (то бишь, != currObj)


            if (conditionFirstRoundNormal | conditionFirstRoundSpecial | conditionSecondRound_BigBlue) {
                System.out.println("Правильно" + "\n");
                points += 2;
                rounds += 1;
                threes.add(choices.get(ans - 1));

                if (threes.size() == 8) {
                    System.out.println("Поздравляем! Вы прошли первый тур! Условием было: " + name_of_game);
                    System.out.println("Набрано очков: " + points);
                    break;
                }

            } else {
                attempts--;
                System.out.println("Неправильно. У вас осталось " + attempts + " попыток" + "\n");
                points -= 1;

                if (attempts == 0) {
                    System.out.println("Игра окончена. Вы дисквалифицированы.");
                    System.out.println("Условием было: " + name_of_game);
                    System.out.println("Набрано очков: " + Math.round(points));
                    break;
                }
            }
        }
    }
}
