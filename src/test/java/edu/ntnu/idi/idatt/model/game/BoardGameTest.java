package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TooManyPlayersException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

  private BoardGame game;

  @BeforeEach
  void setUp() {
    game = new BoardGame(board, dice);
  }

  @Test
  void constructor_initializesPlayerList() {
    assertNotNull(game.getPlayers(), "Player list should be initialized.");
  }

  @Test
  void addPlayer_addsPlayerSuccessfully() {
    Player player = new Player("Test Player", game, LocalDate.of(2000, 1, 1));
    game.addPlayer(player);
    assertTrue(game.getPlayers().contains(player), "Player should be added to the player list.");
  }

  @Test
  void createBoard_creates30Tiles() {
    game.createBoard();
    Board board = game.getBoard();
    assertNotNull(board, "Board should be created.");
    assertEquals(30, board.size(), "Board should have 30 tiles.");
  }

  @Test
  void createDice_createsDiceObject() {
    game.createDice();
    Dice dice = game.getDice();
    assertNotNull(dice, "Dice should be created.");
  }

  @Test
  void getBoard_returnsBoard() {
    game.createBoard();
    assertNotNull(game.getBoard(), "getBoard() should return a Board object.");
  }

  @Test
  void getDice_returnsDice() {
    game.createDice();
    assertNotNull(game.getDice(), "getDice() should return a Dice object.");
  }

  @Test
  void getPlayers_returnsPlayerList() {
    assertNotNull(game.getPlayers(), "getPlayers() should return a list of players.");
  }

  @Test
  void playWithoutInitThrows() {
    assertThrows(GameNotInitializedException.class, () -> game.play());
  }

  @Test
  void playWithoutPlayersThrows() {
    game.createBoard();
    game.createDice();
    assertThrows(NoPlayersException.class, () -> game.play());
  }

  @Test
  void tooManyPlayersThrows() {
    for (int i = 0; i < BoardGame.MAX_PLAYERS; i++) {
      game.addPlayer(new Player("P"+i, game, LocalDate.of(2000, 1, 1)));
    }
    assertThrows(TooManyPlayersException.class,
        () -> game.addPlayer(new Player("overflow", game, LocalDate.of(2000, 1, 1))));
  }

  @Test
  void playOneTurnWithoutInitThrows() {
    BoardGame g = new BoardGame(board, dice);
    assertThrows(GameNotInitializedException.class, () -> g.playOneTurn());
  }

  @Test
  void playOneTurnWithoutPlayersThrows() {
    BoardGame g = new BoardGame(board, dice);
    g.createBoard();
    g.createDice();
    assertThrows(NoPlayersException.class, () -> g.playOneTurn());
  }

  @Test
  void playOneTurnAdvancesCurrentPlayer() {
    BoardGame g = new BoardGame(board, dice);
    g.createBoard();
    g.createDice();
    Player p1 = new Player("A", g, LocalDate.of(2000, 1, 1));
    Player p2 = new Player("B", g, LocalDate.of(2000, 1, 1));
    g.addPlayer(p1);
    g.addPlayer(p2);

    g.playOneTurn();
    assertEquals(p1, g.getCurrentPlayer());

    g.playOneTurn();
    assertEquals(p2, g.getCurrentPlayer());
  }

  @Test
  void playOneTurnFiresEvents() {
    BoardGame g = new BoardGame(board, dice);
    g.createBoard();
    g.createDice();
    Player p = new Player("A", g, LocalDate.of(2000, 1, 1));
    g.addPlayer(p);

    List<BoardGameEvent> evts = new ArrayList<>();
    g.addObserver((game, e) -> evts.add(e));

    g.playOneTurn();
    assertTrue(evts.contains(BoardGameEvent.GAME_START));
    assertTrue(evts.contains(BoardGameEvent.DICE_ROLLED));
    assertTrue(evts.contains(BoardGameEvent.PLAYER_MOVED));
  }

}
