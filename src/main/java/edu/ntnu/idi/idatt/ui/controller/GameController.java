package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.ui.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.ui.view.layouts.BoardView;
import java.util.Comparator;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameController handles the game flow logic: whose turn it is, dice roll, and movement.
 * It does not handle GUI logic.
 */
public class GameController  {

  private final GameEngine engine;

  public GameController(GameEngine engine) {
    this.engine = engine;
  }

  public void playTurn() {
    engine.handleTurn();
  }

  public boolean isGameOver() {
    return engine.isGameOver();
  }

  public Player getCurrentPlayer() {
    return engine.getCurrentPlayer();
  }

  public Player getWinner() {
    return engine.checkWinCondition();
  }

  public GameEngine getEngine() {
    return engine;
  }
}