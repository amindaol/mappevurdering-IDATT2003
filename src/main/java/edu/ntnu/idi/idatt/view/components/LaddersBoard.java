package edu.ntnu.idi.idatt.view.components;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class LaddersBoard {

  private final GridPane grid;
  private final Map<Integer, Pane> tileMap = new HashMap<>();

  public LaddersBoard(int rows, int cols) {
    grid = new GridPane();

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int indexInRow = (r % 2 == 0)
            ? c
            : (cols - 1 - c);

        int tileId = r * cols + indexInRow + 1;

        Pane cell = new Pane();
        cell.setPrefSize(80, 80);
        cell.getStyleClass().add("tile");

        int guiRow = rows - 1 - r; // Invert the row index for GUI representation
        grid.add(cell, c, guiRow);
        tileMap.put(tileId, cell);
      }
    }
  }

  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }

  public GridPane getGrid() {
    return grid;
  }
}
