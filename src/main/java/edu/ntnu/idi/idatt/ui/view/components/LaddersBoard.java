package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Ladder;
import edu.ntnu.idi.idatt.model.game.Tile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LaddersBoard {

  private final GridPane grid;
  private final Map<Integer, Pane> tileMap = new HashMap<>();
  private static final double TILE_SIZE = 80;
  private final int rows;
  private final int cols;
  private final Pane overlay = new Pane();
  private final StackPane container;


  public LaddersBoard(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new GridPane();
    this.grid.setAlignment(Pos.CENTER);
    this.overlay.setPickOnBounds(false);
    this.container = new StackPane(grid, overlay);

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int indexInRow = (r % 2 == 0) ? c : (cols - 1 - c);
        int tileId = r * cols + indexInRow + 1;

        Pane tile = new Pane();
        tile.setPrefSize(TILE_SIZE, TILE_SIZE);
        tile.getStyleClass().add("tile");

        int guiRow = rows - 1 - r;
        grid.add(tile, c, guiRow);
        tileMap.put(tileId, tile);
      }
    }
  }


  public void drawLadders(List<Ladder> ladders) {
    grid.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
      redrawLaddersAndSnakes(ladders, List.of());  // replace with actual snakes if needed
    });
  }

  public void drawSnakes(List<Ladder> snakes) {
    grid.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
      redrawLaddersAndSnakes(List.of(), snakes);  // replace with actual ladders if needed
    });
  }

  // Method to handle actual redrawing with proper coordinates
  private void redrawLaddersAndSnakes(List<Ladder> ladders, List<Ladder> snakes) {
    overlay.getChildren().clear();

    Platform.runLater(() -> {
      for (Ladder ladder : ladders) {
        drawConnection(ladder, Color.DARKSEAGREEN);
      }
      for (Ladder snake : snakes) {
        drawConnection(snake, Color.CRIMSON);
      }
    });
  }

  // Helper method to draw individual ladder or snake lines
  private void drawConnection(Ladder connection, Color color) {
    Pane fromPane = tileMap.get(connection.getFromTileId());
    Pane toPane = tileMap.get(connection.getToTileId());

    if (fromPane == null || toPane == null) {
      return;
    }

    Bounds fromBounds = fromPane.localToScene(fromPane.getBoundsInLocal());
    Bounds toBounds = toPane.localToScene(toPane.getBoundsInLocal());
    Bounds containerBounds = container.localToScene(container.getBoundsInLocal());

    double startX = fromBounds.getCenterX() - containerBounds.getMinX();
    double startY = fromBounds.getCenterY() - containerBounds.getMinY();
    double endX = toBounds.getCenterX() - containerBounds.getMinX();
    double endY = toBounds.getCenterY() - containerBounds.getMinY();

    Line connectionLine = new Line(startX, startY, endX, endY);
    connectionLine.setStrokeWidth(5);
    connectionLine.setStroke(color);
    connectionLine.setOpacity(0.7);

    overlay.getChildren().add(connectionLine);
  }


  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }

  public GridPane getGrid() {
    return grid;
  }

  public StackPane getBoardWithOverlay() {
    return container;
  }

}
