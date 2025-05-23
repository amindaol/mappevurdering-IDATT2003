package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Ladder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * A visual board component for games like Love & Ladders or PointBattles.
 * Internally uses a {@link GridPane} to lay out tiles and an overlay {@link Pane}
 * to draw ladders and snakes as lines between tiles.
 * Tiles are arranged in a zigzag pattern (left-to-right, then right-to-left per row),
 * and each tile is mapped to a unique tile ID.
 * Used in the board view to render the game layout and visual effects.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class LaddersBoard {

  private final GridPane grid;
  private final Map<Integer, Pane> tileMap = new HashMap<>();
  private static final double TILE_SIZE = 80;
  private final Pane overlay = new Pane();
  private final StackPane container;

  /**
   * Constructs a new board with the given number of rows and columns.
   * Tiles are created and added in zigzag layout order.
   *
   * @param rows number of board rows
   * @param cols number of board columns
   */
  public LaddersBoard(int rows, int cols) {
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

  /**
   * Draws green ladder lines between tile connections.
   *
   * @param ladders list of ladder connections to draw
   * @throws IllegalArgumentException if ladders contain invalid tile IDs
   */
  public void drawLadders(List<Ladder> ladders) {
    if (ladders == null) {
      throw new IllegalArgumentException("Ladders list cannot be null");
    }

    grid.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->
        redrawLaddersAndSnakes(ladders, List.of()));
  }

  /**
   * Draws red snake lines between tile connections.
   *
   * @param snakes list of snake connections to draw
   * @throws IllegalArgumentException if snakes contain invalid tile IDs
   */

  public void drawSnakes(List<Ladder> snakes) {
    if (snakes == null) {
      throw new IllegalArgumentException("Snakes list cannot be null");
    }

    grid.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->
        redrawLaddersAndSnakes(List.of(), snakes));
  }

  /**
   * Clears and redraws ladder and snake lines on the overlay layer.
   *
   * @param ladders list of ladders to draw
   * @param snakes list of snakes to draw
   */
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

  /**
   * Draws a visual connection between two tiles using a line.
   *
   * @param connection the ladder or snake to render
   * @param color the color of the line (green for ladder, red for snake)
   * @throws IllegalArgumentException if the connection's fromTileId or toTileId are invalid
   */
  private void drawConnection(Ladder connection, Color color) {
    Pane fromPane = tileMap.get(connection.getFromTileId());
    Pane toPane = tileMap.get(connection.getToTileId());

    if (fromPane == null || toPane == null) {
      throw new IllegalArgumentException("Invalid tile ID for ladder or snake connection");
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

  /**
   * Returns the tile pane associated with the given tile ID.
   *
   * @param tileId the tile ID to look up
   * @return the tile pane, or null if not found
   */
  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }

  /**
   * Returns the full board layout including the overlay for snakes/ladders.
   *
   * @return the board as a StackPane
   */
  public StackPane getBoardWithOverlay() {
    return container;
  }

}
