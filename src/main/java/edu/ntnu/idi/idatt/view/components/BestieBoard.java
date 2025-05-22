package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Tile;
import java.awt.Label;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class BestieBoard {

  private final GridPane grid;
  private final Map<Integer, Pane> tileMap = new HashMap<>();
  private static final double TILE_SIZE = 80;
  private final int rows;
  private final int cols;
  private final Pane overlay = new Pane();
  private final StackPane container;

  public BestieBoard(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new GridPane();
    this.grid.setAlignment(Pos.CENTER);
    this.overlay.setPickOnBounds(false);
    this.container = new StackPane(grid, overlay);

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int tileId = r * cols + c + 1;

        Pane tile = new Pane();
        tile.setPrefSize(TILE_SIZE, TILE_SIZE);
        tile.getStyleClass().add("tile");

        grid.add(tile, c, r);
        tileMap.put(tileId, tile);
      }
    }
  }


  public void drawCoinsTiles(int tileId, List<Tile> coinTiles) {
    javafx.application.Platform.runLater(() -> {
      Pane tilePane = tileMap.get(tileId);
      if (tilePane != null) {
        tilePane.getStyleClass().add("coin-tile");
      }
    });
  }

  public GridPane getGrid() {
    return grid;
  }

  public StackPane getBoardWithOverlay() {
    return container;
  }

  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }
}
