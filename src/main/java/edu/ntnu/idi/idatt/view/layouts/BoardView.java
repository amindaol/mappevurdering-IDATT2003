package edu.ntnu.idi.idatt.view.layouts;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;

public class BoardView {

  private final StackPane root;
  private final GridPane grid;
  private final Pane tokenLayer;
  private final Map<Integer, StackPane> tilePanes = new HashMap<>();

  public BoardView(int rows, int cols) {
    grid = new GridPane();
    tokenLayer = new Pane();
    root = new StackPane(grid, tokenLayer);

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int indexInRow = (r % 2 == 0)
            ? c
            : (cols - 1 - c);

        int tileId = r * cols + indexInRow + 1;

        StackPane cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.getStyleClass().add("tile");
        grid.add(cell, c, r);
        tilePanes.put(tileId, cell);
      }
    }
  }

  public StackPane getRoot() {
    return root;
  }

  public void placeToken(int tileId, Node tokenNode) {
    StackPane cell = tilePanes.get(tileId);
    if (cell == null) {
      throw new IllegalArgumentException("No such tile: " + tileId);
    }
    tokenNode.getStyleClass().add("player-token");
    Point2D pos = cell.localToParent(0,0);
    tokenLayer.getChildren().add(tokenNode);
    tokenNode.relocate(pos.getX(), pos.getY());
  }

  public void moveToken(Node tokenNode, int tileId) {
    StackPane cell = tilePanes.get(tileId);
    if (cell == null) {
      throw new IllegalArgumentException("No such tile: " + tileId);
    }
    Point2D pos = cell.localToParent(0,0);
    tokenNode.relocate(pos.getX(), pos.getY());
  }
 }
