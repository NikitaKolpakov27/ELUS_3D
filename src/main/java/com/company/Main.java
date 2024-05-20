package com.company;

import com.company.service.FirstTour;
import com.company.service.Game;
import com.company.service.GameProcess;
import com.company.service.SecondTour;

public class Main {

    public static void main(String[] args) {
        GameProcess gameProcess = new GameProcess();
        FirstTour firstTour = new FirstTour(gameProcess);
//        SecondTour secondTour = new SecondTour(gameProcess);
        Game game = new Game(firstTour);
        game.getTour().startTour();

    }
}
