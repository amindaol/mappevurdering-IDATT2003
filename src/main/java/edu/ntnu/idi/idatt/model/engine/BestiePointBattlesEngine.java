package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.core.LinearMovement;
import edu.ntnu.idi.idatt.core.Movement;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;

import java.util.Comparator;
import java.util.List;


/**
 * Game engine for "Bestie Point Battles":
 * - Game ends when a player reaches last tile
 * - Winner is the one with the highest score at that moment
 */
public class BestiePointBattlesEngine extends GameEngine {

  private final Dice dice;
  private final Movement movement = new LinearMovement();

  public BestiePointBattlesEngine(BoardGame game, Dice dice) {
    super(game);
    if (dice == null)
      throw new NullPointerException("Dice cannot be null.");
    this.dice = dice;
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

    notifyObservers(BoardGameEvent.GAME_START);

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

    int roll = dice.roll().stream().mapToInt(Integer::intValue).sum();
    notifyObservers(BoardGameEvent.DICE_ROLLED);

    movement.move(player, roll);
    notifyObservers(BoardGameEvent.PLAYER_MOVED);

    if (player.getCurrentTile().equals(board.getLastTile())) {
      notifyObservers(BoardGameEvent.GAME_ENDED);
      notifyObservers(BoardGameEvent.GAME_WON);
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
}
