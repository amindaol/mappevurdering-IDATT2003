package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.core.LinearMovement;
import edu.ntnu.idi.idatt.model.core.Movement;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;

import java.util.Comparator;
import java.util.List;


/**
 * Game engine for "Bestie Point Battles": - Game ends when a player reaches last tile - Winner is
 * the one with the highest score at that moment
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestiePointBattlesEngine extends GameEngine {

  private final Dice dice;
  private final Movement movement = new LinearMovement();
  private final int maxStars = 3;

  public BestiePointBattlesEngine(BoardGame boardGame, Dice dice) {
    super(boardGame);
    this.dice = dice;
  }

  @Override
  public void playGame() {
    throw new UnsupportedOperationException("BestieBattles uses manual playTurn()");
  }

  @Override
  public void handleTurn(int total) {
    if (gameOver) {
      return;
    }

    Player currentPlayer = getCurrentPlayer();

    notifyObservers(BoardGameEvent.DICE_ROLLED);

    movement.move(currentPlayer, total);
    notifyObservers(BoardGameEvent.PLAYER_MOVED);

    Tile currentTile = currentPlayer.getCurrentTile();
    if (currentTile.getAction() != null) {
      currentTile.getAction().perform(currentPlayer);
    }

    if (checkWinCondition() != null) {
      notifyObservers(BoardGameEvent.GAME_WON);
      notifyObservers(BoardGameEvent.GAME_ENDED);
      endGame();
    } else {
      nextPlayer();
    }
  }

  @Override
  public Player checkWinCondition() {
    return players.stream()
        .filter(p -> p instanceof BestiePlayer)
        .map(p -> (BestiePlayer) p)
        .filter(bp -> bp.getStars() >= maxStars)
        .findFirst()
        .orElse(null);
  }

  public Dice getDice() {
    return dice;
  }
}
