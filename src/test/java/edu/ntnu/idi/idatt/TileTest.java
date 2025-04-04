package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.game.BoardGame;
import edu.ntnu.idi.idatt.game.Player;
import edu.ntnu.idi.idatt.game.Tile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

  private Tile tile;
  private Player player;
  private BoardGame game;

  @BeforeEach
  void setUp() {
    game = new BoardGame();
    tile = new Tile(1);
    player = new Player("Test player", game);
  }
}
