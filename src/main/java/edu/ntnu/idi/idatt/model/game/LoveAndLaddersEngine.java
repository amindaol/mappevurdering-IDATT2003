package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;

import java.util.List;

public class LoveAndLaddersEngine extends GameEngine {

  private final Dice dice;
  private final List<BoardGameObserver> observers;

  public LoveAndLaddersEngine(Board board, List<Player> players, Dice dice, List<BoardGameObserver> observers) {
    super(board, players);
    if (dice == null) throw new NullPointerException("dice cannot be null");
    this.dice = dice;
    this.observers = observers;
  }

  @Override
  public void playGame() {
    if (board == null || dice == null) throw new GameNotInitializedException();
    if (players == null || players.isEmpty()) throw new NoPlayersException();

    Tile start = board.getStartTile();
    for (Player player : players) {
      player.placeOnTile(start);
    }

    notify(BoardGameEvent.GAME_START);

    while (!gameOver) {
      handleTurn();
    }
  }

  @Override
  public void handleTurn() {
    Player player = getCurrentPlayer();

    if (player.isSkipNextTurn()) {
      player.setSkipNextTurn(false);
      nextPlayer();
      return;
    }

    int roll = dice.roll();
    notify(BoardGameEvent.DICE_ROLLED);

    player.move(roll);
    notify(BoardGameEvent.PLAYER_MOVED);

    if (checkWinCondition() != null) {
      notify(BoardGameEvent.GAME_WON);
      notify(BoardGameEvent.GAME_ENDED);
      endGame();
    } else {
      nextPlayer();
    }
  }

  @Override
  public Player checkWinCondition() {
    Tile last = board.getLastTile();
    return players.stream()
        .filter(p -> p.getCurrentTile() == last)
        .findFirst()
        .orElse(null);
  }

  private void notify(BoardGameEvent event) {
    for (BoardGameObserver observer : observers) {
      observer.onGameStateChange(null, event);
    }
  }
}
