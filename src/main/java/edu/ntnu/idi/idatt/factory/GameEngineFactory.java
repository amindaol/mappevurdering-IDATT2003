package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.game.*;

/**
 * Factory class for creating GameEngine instances based on the selected game mode.
 *
 * <p>This class is responsible for creating the appropriate GameEngine instance based on the
 * selected game mode. It uses the factory design pattern to encapsulate the instantiation logic.
 *
 *  @author Aminda Lunde
 *  @author Ingrid Opheim
 *  @version 1.0
 */
public final class GameEngineFactory {

  /**
   * Private constructor to prevent instantiation of the factory class.
   */
  private GameEngineFactory() {

  }

  /**
   * Creates the correct GameEngine instance based on the selected mode.
   *
   * @param game the BoardGame containing players, board, dice, observers
   * @param mode the selected game mode
   * @return a fully initialized GameEngine
   */
  public static GameEngine create(BoardGame game, GameMode mode) {
    return switch (mode) {
      case LOVE_AND_LADDERS -> new LoveAndLaddersEngine(
          game,
          game.getDice()
      );

      case BESTIE_POINT_BATTLES -> new BestiePointBattlesEngine(
          game,
          game.getDice()
      );
    };
  }
}
