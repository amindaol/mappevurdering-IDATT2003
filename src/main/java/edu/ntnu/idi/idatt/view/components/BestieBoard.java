package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


/**
 * The BestieBoard class is responsible for rendering the game board in the GUI. It uses a GridPane
 * to layout the tiles and an overlay for additional effects. The class provides methods to draw the
 * board, add coins and shop tiles, and manage tile visibility.
 */
public class BestieBoard {

  private static final double TILE_SIZE = 80;

  private final GridPane grid;
  private final StackPane container;

  private final Map<Integer, Pane> tileMap = new HashMap<>();
  private final Map<String, Integer> positionToTileId = new HashMap<>();
  private int rows;
  private int cols;

  /**
   * Constructs a BestieBoard with the specified number of rows and columns.
   *
   * @param rows the number of rows in the board
   * @param cols the number of columns in the board
   */
  public BestieBoard(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new GridPane();
    this.grid.setAlignment(Pos.CENTER);
    Pane overlay = new Pane();
    overlay.setPickOnBounds(false);
    this.container = new StackPane(grid, overlay);
  }

  /**
   * Draws the tiles from the board model and hides unused positions.
   *
   * @param boardModel the board model containing the tiles to be drawn.
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
      Pane tilePane = new Pane();
      tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);
      tilePane.getStyleClass().add("tile");

      int row = tile.getRow();
      int col = tile.getCol();
      int id = tile.getTileId();

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

  /**
   * Draws a tile with a coin effect.
   *
   * @param tileId the ID of a tile to be drawn with a coin effect
   */
  public void drawCoinsTile(int tileId) {
    Platform.runLater(() -> {
      Pane tilePane = tileMap.get(tileId);
      if (tilePane != null) {
        tilePane.getStyleClass().add("coin-tile");
      }
    });
  }

  /**
   * Draws a shop tile.
   *
   * @param tileId the ID of a tile to be drawn as a shop tile
   */
  public void drawShopTile(int tileId) {
    Platform.runLater(() -> {
      Pane tilePane = tileMap.get(tileId);
      if (tilePane != null) {
        tilePane.getStyleClass().add("shop-tile");
      }
    });
  }

  /**
   * Returns the StackPane containing the board and overlay.
   *
   * @return the StackPane containing the board and overlay
   */
  public StackPane getBoardWithOverlay() {
    return container;
  }

  /**
   * Returns the tile pane for a given tile ID.
   *
   * @param tileId the ID of the tile
   * @return the Pane representing the tile
   */
  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }
}
