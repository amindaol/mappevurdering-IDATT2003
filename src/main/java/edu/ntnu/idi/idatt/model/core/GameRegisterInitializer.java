package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.config.GameInformation;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.factory.BoardFactory;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import java.util.List;


/**
 * The GameRegisterInitializer class is responsible for initializing the game register with
 * predefined game information. It registers different games with their respective rules, player
 * limits, engine factories, board options, and game modes.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class GameRegisterInitializer {

  private GameRegisterInitializer() {

  }

  /**
   * Initializes the game register with predefined game information. This method registers two
   * games: 1. Love & Ladders: A classic snakes and ladders game where the first player to tile 90
   * wins. 2. Bestie Point Battles: A game where players collect points on tiles, and the first
   * player to reach the last tile ends the game.
   *
   * @param register the GameRegister instance to initialize
   */
  public static void initialize(GameRegister register) {

    register.register(new GameInformation(
        "Love & Ladders",
        "Classic snakes and ladders. First player to tile 90 wins. Some tiles have special "
            + "actions.",
        6,
        2,
        gameConfiguration -> new LoveAndLaddersEngine(
            gameConfiguration.getBoardGame(),
            gameConfiguration.getBoardGame().getDice()
        ),
        () -> List.of(BoardFactory.createLoveAndLaddersBoard()),
        GameMode.LOVE_AND_LADDERS
    ));

    register.register(new GameInformation(
        "Bestie Point Battles",
        "Collect the most points! Every tile has points. First player to the last tile ends "
            + "the game.",
        6,
        2,
        gameConfiguration -> new BestiePointBattlesEngine(
            gameConfiguration.getBoardGame(),
            gameConfiguration.getBoardGame().getDice()
        ),
        () -> List.of(BoardFactory.createBestiePointBattlesBoard()),
        GameMode.BESTIE_POINT_BATTLES
    ));
  }
}
