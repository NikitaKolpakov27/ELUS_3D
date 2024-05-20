package com.company.service;

import com.company.enums.Size;
import com.company.model.Figure;

import java.util.List;
import java.util.Random;

import static com.company.service.Game.ID_BIG_BLUE;
import static com.company.service.Game.ID_SMALL_BLUE;

public class SecondTour implements Tour {

    public GameProcess gameProcess;
    public SecondTour(GameProcess gameProcess) {
        this.gameProcess = gameProcess;
    }

    // 2-й раунд. Игра по размеру (если BIG => BLUE, SMALL => YELLOW)
    private void gameBySize(Size currSize, int id) {
        List<Figure> threes = Tools.makeThrees_2nd(currSize);
        List<List<Figure>> answers = Tools.makeAnswers_2nd(threes, currSize);
        List<Figure> rightAnswers = answers.get(0);
        List<Figure> wrongAnswers = answers.get(1);

        this.gameProcess.playRound(currSize, rightAnswers, wrongAnswers, threes, id);
    }

    @Override
    public void startTour() {
        //Рандомно получаем, как будем играть (по цвету, размеру или типу) (пока не используется)
        Object obj = null;
        Random random = new Random();
        int rand = random.nextInt(3);

        //Пока только с размером балуемся
        int ran = random.nextInt(2);
        Size currSize;
        Object choosableObj;

        ran = 1; // специально делаем пока что ток одно условие

        //на данный момент добавлено только условие "если БИГ ->  СИНИЙ, иначе -> ЖЁЛТЫЙ"
        if (ran == 0) {
            currSize = Size.BIG;
            gameBySize(currSize, ID_BIG_BLUE);
        } else { // условие "если SMALL ->  СИНИЙ, иначе -> ЖЁЛТЫЙ"
            currSize = Size.SMALL;
            gameBySize(currSize, ID_SMALL_BLUE);
        }
    }
}
