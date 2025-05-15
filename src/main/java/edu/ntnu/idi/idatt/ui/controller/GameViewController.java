package edu.ntnu.idi.idatt.ui.controller;


import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.ui.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.ui.view.layouts.BoardView;
import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameViewController implements BoardGameObserver {


  private final GameEngine engine;
  private final BoardView boardView;
  private final Map<Player, PlayerIcon> playerIcons = new HashMap<>();

  public GameViewController(GameController controller, BoardView boardView) {
    this.engine = controller.getEngine();
    this.boardView = boardView;

    engine.getBoard().addObserver(this); // If your board/game has observer – or do it elsewhere
    initializeBoard();
    initializePlayers();
  }

  private void initializeBoard() {
    boardView.drawBoard(engine.getBoard());
  }

  private void initializePlayers() {
    for (Player player : engine.getPlayers()) {
      String path = "/icons/players/" + player.getToken().getIconFileName();
      Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
      PlayerIcon icon = new PlayerIcon(player.getName(), image);
      playerIcons.put(player, icon);

      Tile tile = player.getCurrentTile();
      if (tile != null) {
        boardView.placePlayerIcon(player.getName(), icon, tile.getTileId());
      }
    }
  }

  @Override
  public void onGameStateChange(Object source, BoardGameEvent event) {
    Platform.runLater(() -> {
      switch (event) {
        case GAME_START -> initializePlayers();
        case PLAYER_MOVED -> updatePlayerPositions();
        case GAME_WON -> highlightWinner();
        default -> {}
      }
    });
  }

  private void updatePlayerPositions() {
    for (Player player : engine.getPlayers()) {
      Tile tile = player.getCurrentTile();
      PlayerIcon icon = playerIcons.get(player);
      if (tile != null && icon != null) {
        boardView.movePlayerIcon(player.getName(), tile.getTileId());
      }
    }
  }

  private void highlightWinner() {
    Player winner = engine.checkWinCondition();
    if (winner != null) {
      System.out.println("🏆 " + winner.getName() + " has won!");
      // Optional: trigger GUI effect
    }
  }
}
