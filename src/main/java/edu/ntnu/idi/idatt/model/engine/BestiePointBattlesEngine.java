package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.action.BuyStarAction;
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
  public void handleTurn(int steps) {
    if (gameOver) {
      return;
    }

    BestiePlayer mover = (BestiePlayer) getCurrentPlayer();
    Tile current = mover.getCurrentTile();

    notifyObservers(BoardGameEvent.DICE_ROLLED);

    for (int i = 0; i < steps; i++) {
      current = current.getNextTile();
      mover.placeOnTile(current);
      notifyObservers(BoardGameEvent.PLAYER_MOVED);

      if (current.getAction() instanceof BuyStarAction shop) {
        if (mover.getCoins() >= 20) {
          shop.perform(mover);      // decrement coins, increment star count
          notifyObservers(BoardGameEvent.PLAYER_MOVED); // to refresh UI
        }
      }
    }
    if (!(current.getAction() instanceof BuyStarAction) && current.getAction() != null) {
      current.getAction().perform(mover);
      notifyObservers(BoardGameEvent.PLAYER_MOVED);
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

  @Override
  public void startGame() {
    Tile start = board.getStartTile();
    for (Player p : players) {
      p.placeOnTile(start);
    }
    notifyObservers(BoardGameEvent.GAME_START);
  }
}
