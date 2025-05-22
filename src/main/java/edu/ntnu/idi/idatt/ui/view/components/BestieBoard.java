package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.TilePane;

/**
 * A simplified version of LaddersBoard, used for Bestie Point Battles.
 * Only provides tile grid functionality without ladder/snake drawing.
 */
public class BestieBoard extends GridPane {

  private final GridPane grid = new GridPane();
  private final Map<Integer, TileComponent> tileMap = new HashMap<>();
  private final int rows;
  private final int cols;
  private final List<Tile> tiles;

  public BestieBoard(List<Tile> tiles, int rows, int cols) {
    this.tiles = tiles;
    this.rows = rows;
    this.cols = cols;
    drawTiles();
  }

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

  public Node getBoardWithOverlay() {
    return grid;
  }

  public TileComponent getTile(int tileId) {
    return tileMap.get(tileId);
  }

  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    TileComponent tile = getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }

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
