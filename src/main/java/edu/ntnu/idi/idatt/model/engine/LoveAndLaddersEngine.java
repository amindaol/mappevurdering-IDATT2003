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
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;

import java.util.List;

public class LoveAndLaddersEngine extends GameEngine {

  private final Dice dice;
  private final Movement movement = new LinearMovement();


  public LoveAndLaddersEngine(BoardGame game, Dice dice) {
    super(game);
    if (dice == null) throw new NullPointerException("dice cannot be null");
    this.dice = dice;
  }

  @Override
  public void playGame() {
    if (board == null || dice == null) throw new GameNotInitializedException();
    if (players == null || players.isEmpty()) throw new NoPlayersException();

    Tile start = board.getStartTile();
    for (Player player : players) {
      player.placeOnTile(start);
    }

    notifyObservers(BoardGameEvent.GAME_START);

    while (!gameOver) {
      handleTurn();
    }
  }

  @Override
  public void handleTurn() {
    Player player = getCurrentPlayer();
    int roll = dice.roll().stream().mapToInt(Integer::intValue).sum();

    if (player.isSkipNextTurn()) {
      player.setSkipNextTurn(false);
      nextPlayer();
      return;
    }

    notifyObservers(BoardGameEvent.DICE_ROLLED);

    movement.move(player, roll);
    notifyObservers(BoardGameEvent.PLAYER_MOVED);

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
    Tile last = board.getLastTile();
    return players.stream()
        .filter(p -> p.getCurrentTile() == last)
        .findFirst()
        .orElse(null);
  }
}
