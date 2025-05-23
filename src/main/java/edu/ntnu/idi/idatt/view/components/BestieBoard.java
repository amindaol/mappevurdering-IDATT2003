package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Tile;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * A simplified board layout used for the Bestie PointBattles game.
 * Unlike {@link LaddersBoard}, this board only handles tile layout and player movement,
 * and does not draw ladders or snakes.
 * Tiles are added to a {@link GridPane} and stored in a map for easy access by tile ID.
 * </p>
 *
 * Used in the BestieBattlesView to display the game board and player icons.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieBoard extends GridPane {

  private final GridPane grid = new GridPane();
  private final Map<Integer, TileComponent> tileMap = new HashMap<>();
  private final int rows;
  private final int cols;
  private final List<Tile> tiles;

  /**
   * Creates a new BestieBoard with the given tile layout and dimensions.
   *
   * @param tiles the list of tiles to display
   * @param rows number of rows in the board
   * @param cols number of columns in the board
   */
  public BestieBoard(List<Tile> tiles, int rows, int cols) {
    this.tiles = tiles;
    this.rows = rows;
    this.cols = cols;
    drawTiles();
  }

  /**
   * Draws all tiles onto the internal grid.
   * Tiles are placed row by row, top to bottom.
   */
  public void drawTiles() {
    grid.setHgap(5);
    grid.setVgap(5);
    grid.getChildren().clear();
    tileMap.clear();

    for (int i = 0; i < tiles.size(); i++) {
      Tile tile = tiles.get(i);
      int tileId = tile.getTileId();
      int row = rows - 1 - (i / cols);
      int col = i % cols;

      TileComponent component = new TileComponent(tile);
      tileMap.put(tileId, component);
      grid.add(component, col, row);
    }
  }

  /**
   * Returns the grid containing all tile components.
   *
   * @return the board node
   */
  public Node getBoardWithOverlay() {
    return grid;
  }

  /**
   * Returns the {@link TileComponent} for a given tile ID.
   *
   * @param tileId the ID of the tile
   * @return the TileComponent or null if not found
   * @throws IllegalArgumentException if the tileId is not found
   */
  public TileComponent getTile(int tileId) {
    if (!tileMap.containsKey(tileId)) {
      throw new IllegalArgumentException("Tile not found: " + tileId);
    }
    return tileMap.get(tileId);
  }

  /**
   * Places a player icon on the specified tile.
   *
   * @param playerName the name of the player (not currently used)
   * @param icon the player's icon component
   * @param tileId the ID of the tile to place the icon on
   */
  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    TileComponent tile = getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }

  /**
   * Moves a player icon from its current tile to a new one.
   * Removes the icon from all tiles before placing it on the target tile.
   *
   * @param playerName the name of the player (not currently used)
   * @param icon the player's icon component
   * @param tileId the ID of the target tile
   */
  public void movePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    for (Node node : grid.getChildren()) {
      if (node instanceof TileComponent tc) {
        tc.getChildren().remove(icon);
      }
    }

    TileComponent tile = getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }
}
