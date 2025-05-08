package edu.ntnu.idi.idatt.model.observer;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardGameObserverTest {

  private BoardGame game;
  private List<BoardGameEvent> events;

  @BeforeEach
  void setUp() {
    game = new BoardGame();
    events = new ArrayList<>();
    game.addObserver((g, evt) -> events.add(evt));
  }

  @Test
  void gameStartFiresEvent() {
    Board tiny = new Board();
    tiny.addTile(new Tile(1));
    game.setBoard(tiny);

    game.createDice();
    game.addPlayer(new Player("Alice", game, LocalDate.of(2000, 1, 1)));

    game.play();

    assertTrue(events.contains(BoardGameEvent.GAME_START),
        "GAME_START should fire when play() begins");
  }

  @Test
  void fullSequenceFiresCorrectly() {
    Board tiny = new Board();
    tiny.addTile(new Tile(1));
    game.setBoard(tiny);

    game.createDice();
    game.addPlayer(new Player("Bob", game, LocalDate.of(2000, 1, 1)));

    game.play();

    assertEquals(5, events.size(), "play() on 1‐tile board must fire 5 events");
    assertEquals(BoardGameEvent.GAME_START,   events.get(0));
    assertEquals(BoardGameEvent.DICE_ROLLED,  events.get(1));
    assertEquals(BoardGameEvent.PLAYER_MOVED, events.get(2));
    assertEquals(BoardGameEvent.GAME_WON,     events.get(3));
    assertEquals(BoardGameEvent.GAME_ENDED,   events.get(4));
  }
}
