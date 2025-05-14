package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.factory.BoardFactory;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;

import java.util.List;


public class GameRegisterInitializer {

  public static void initialize(GameRegister register) {

    register.register(new GameInformation(
        "Love & Ladders",
        "Classic snakes and ladders. First player to tile 90 wins. Some tiles have special actions.",
        6,
        2,
        gameConfiguration -> new LoveAndLaddersEngine(
            gameConfiguration.getBoardGame().getBoard(),
            gameConfiguration.getBoardGame().getPlayers(),
            gameConfiguration.getBoardGame().getDice(),
            gameConfiguration.getBoardGame().getObservers()
        ),
        () -> List.of(BoardFactory.createLoveAndLaddersBoard()),
        GameMode.LOVE_AND_LADDERS
    ));

    register.register(new GameInformation(
        "Bestie Point Battles",
        "Collect the most points! Every tile has points. First player to the last tile ends the game.",
        6,
        2,
        gameConfiguration -> new BestiePointBattlesEngine(
            gameConfiguration.getBoardGame().getBoard(),
            gameConfiguration.getBoardGame().getPlayers(),
            gameConfiguration.getBoardGame().getDice(),
            gameConfiguration.getBoardGame().getObservers()
        ),
        () -> List.of(BoardFactory.createBestiePointBattlesBoard()),
        GameMode.BESTIE_POINT_BATTLES
    ));
  }
}
