package edu.ntnu.idi.idatt.game.observer;

import edu.ntnu.idi.idatt.game.BoardGame;
import java.util.EventListener;

/**
 * Observer interface for the BoardGame class.
 *
 * <p>This interface allows observers to be notified of changes of the BoardGame.
 */
public interface BoardGameObserver extends EventListener {

  /**
   * Called when the game state changes.
   *
   * @param game the BoardGame instance that changed
   */
  void onGameStateChange(BoardGame game);

}
