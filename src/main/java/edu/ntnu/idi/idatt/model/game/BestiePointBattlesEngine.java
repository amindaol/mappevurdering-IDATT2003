package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Game engine for "Bestie Point Battles":
 * - Game ends when a player reaches last tile
 * - Winner is the one with the highest score at that moment
 */
public class BestiePointBattlesEngine extends GameEngine {

  private final Dice dice;
  private final List<BoardGameObserver> observers;

  public BestiePointBattlesEngine(Board board, List<Player> players, Dice dice, List<BoardGameObserver> observers) {
    super(board, players);
    if (dice == null)
      throw new NullPointerException("Dice cannot be null.");
    this.dice = dice;
    this.observers = observers;
  }

  @Override
  public void playGame() {
    if (board == null || players == null || players.isEmpty()) {
      throw new GameNotInitializedException();
    }

    Tile start = board.getStartTile();
    for (Player player : players) {
      player.placeOnTile(start);
    }

    notify(BoardGameEvent.GAME_START);

    while(!gameOver) {
      handleTurn();
    }
  }

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

    if (player.getCurrentTile().equals(board.getLastTile())) {
      notify(BoardGameEvent.GAME_ENDED);
      notify(BoardGameEvent.GAME_WON);
      endGame();
    } else {
      nextPlayer();
    }
  }

  @Override
  public Player checkWinCondition() {
    if (!gameOver) return null;
    return players.stream()
        .max(Comparator.comparingInt(Player::getPoints))
        .orElse(null);
  }

  private void notify(BoardGameEvent event) {
    for (BoardGameObserver observer : observers) {
      observer.onGameStateChange(null, event);
    }
  }
}
