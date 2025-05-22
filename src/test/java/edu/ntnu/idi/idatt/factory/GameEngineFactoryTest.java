package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineFactoryTest {

  @Test
  void testCreateLoveAndLaddersEngine() {
    BoardGame game = new BoardGame(new Board(2, 2), new Dice(1));
    GameEngine engine = GameEngineFactory.create(game, GameMode.LOVE_AND_LADDERS);

    assertNotNull(engine);
    assertTrue(engine instanceof LoveAndLaddersEngine);
  }

  @Test
  void testCreateBestiePointBattlesEngine() {
    BoardGame game = new BoardGame(new Board(3, 3), new Dice(2));
    GameEngine engine = GameEngineFactory.create(game, GameMode.BESTIE_POINT_BATTLES);

    assertNotNull(engine);
    assertTrue(engine instanceof BestiePointBattlesEngine);
  }
}
