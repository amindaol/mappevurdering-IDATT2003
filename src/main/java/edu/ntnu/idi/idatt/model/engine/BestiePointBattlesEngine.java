package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.action.BuyStarAction;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;


/**
 * Game engine for "Bestie Point Battles": - Game ends when a player reaches last tile - Winner is
 * the one with the highest score at that moment.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestiePointBattlesEngine extends GameEngine {

  private final Dice dice;
  private static final int maxStars = 3;

  /**
   * Constructs a BestiePointBattlesEngine with the specified board game and dice.
   *
   * @param boardGame the board game to be played
   * @param dice      the dice used in the game
   */
  public BestiePointBattlesEngine(BoardGame boardGame, Dice dice) {
    super(boardGame);
    this.dice = dice;
  }

  /**
   * Initializes the game engine with the specified players and movement strategy.
   */
  @Override
  public void playGame() {
    throw new UnsupportedOperationException("BestieBattles uses manual playTurn()");
  }

  /**
   * Handles the player's turn by rolling the dice and moving the player the specified number of
   * steps.
   *
   * @param steps the number of steps to move the player
   */
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

      if (current.getAction() instanceof BuyStarAction shop && mover.getCoins() >= 20) {
        shop.perform(mover);  // decrement coins, increment star count
        notifyObservers(BoardGameEvent.PLAYER_MOVED); // to refresh UI
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

  /**
   * Checks if a player has won the game by reaching the maximum number of stars.
   *
   * @return the winning player, or null if no player has won
   */
  @Override
  public Player checkWinCondition() {
    return players.stream()
        .filter(BestiePlayer.class::isInstance)
        .map(p -> (BestiePlayer) p)
        .filter(bp -> bp.getStars() >= maxStars)
        .findFirst()
        .orElse(null);
  }

  /**
   * Returns the dice used in the game.
   *
   * @return the dice used in the game
   */
  public Dice getDice() {
    return dice;
  }


  /**
   * Starts the game by placing each player on the starting tile.
   */
  @Override
  public void startGame() {
    Tile start = board.getStartTile();
    for (Player p : players) {
      p.placeOnTile(start);
    }
    notifyObservers(BoardGameEvent.GAME_START);
  }
}
