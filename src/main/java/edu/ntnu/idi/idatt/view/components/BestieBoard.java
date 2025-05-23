package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestieBoard {

  private static final double TILE_SIZE = 80;

  private final GridPane grid;
  private final Pane overlay = new Pane();
  private final StackPane container;

  private final Map<Integer, Pane> tileMap = new HashMap<>();
  private final Map<String, Integer> positionToTileId = new HashMap<>();
  private int rows;
  private int cols;

  public BestieBoard(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new GridPane();
    this.grid.setAlignment(Pos.CENTER);
    this.overlay.setPickOnBounds(false);
    this.container = new StackPane(grid, overlay);
  }

  /**
   * Draws the tiles from the board model and hides unused positions.
   */
  public void drawBoard(Board boardModel) {
    this.rows = boardModel.getRows();
    this.cols = boardModel.getCols();

    // Clear previous content
    grid.getChildren().clear();
    tileMap.clear();
    positionToTileId.clear();

    // Map used tiles and layout
    for (Tile tile : boardModel.getTiles()) {
      int id = tile.getTileId();
      int row = tile.getRow();
      int col = tile.getCol();

      Pane tilePane = new Pane();
      tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);
      tilePane.getStyleClass().add("tile");

      grid.add(tilePane, col, row);
      tileMap.put(id, tilePane);
      positionToTileId.put(row + "," + col, id);
    }

    // Fill in invisible tiles for empty spots
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        String key = r + "," + c;
        if (!positionToTileId.containsKey(key)) {
          Pane hiddenTile = new Pane();
          hiddenTile.setPrefSize(TILE_SIZE, TILE_SIZE);
          hiddenTile.setVisible(false);
          hiddenTile.setManaged(false);
          grid.add(hiddenTile, c, r);
        }
      }
    }
  }

  public void drawCoinsTile(int tileId) {
    Platform.runLater(() -> {
      Pane tilePane = tileMap.get(tileId);
      if (tilePane != null) {
        tilePane.getStyleClass().add("coin-tile");
      }
    });
  }

  public void drawShopTile(int tileId) {
    Platform.runLater(() -> {
      Pane tilePane = tileMap.get(tileId);
      if (tilePane != null) {
        tilePane.getStyleClass().add("shop-tile");
      }
    });
  }

  public StackPane getBoardWithOverlay() {
    return container;
  }

  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }

  public GridPane getGrid() {
    return grid;
  }

  public Integer getTileIdForPosition(int row, int col) {
    return positionToTileId.get(row + "," + col);
  }
}
