package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Ladder;
import edu.ntnu.idi.idatt.model.game.Tile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    javafx.application.Platform.runLater(() -> {
      for (Ladder ladder : ladders) {
        Pane fromPane = tileMap.get(ladder.getFromTileId());
        Pane toPane = tileMap.get(ladder.getToTileId());

        if (fromPane == null || toPane == null) continue;

        double startX = fromPane.getLayoutX() + fromPane.getWidth() / 2;
        double startY = fromPane.getLayoutY() + fromPane.getHeight() / 2;
        double endX = toPane.getLayoutX() + toPane.getWidth() / 2;
        double endY = toPane.getLayoutY() + toPane.getHeight() / 2;

        Line ladderLine = new Line(startX, startY, endX, endY);
        ladderLine.setStrokeWidth(5);
        ladderLine.setStroke(Color.DARKSEAGREEN);
        ladderLine.setOpacity(0.7);

        overlay.getChildren().add(ladderLine);
      }
    });
  }

  public void drawSnakes(List<Ladder> snakes) {
    javafx.application.Platform.runLater(() -> {
      for (Ladder snake : snakes) {
        Pane fromPane = tileMap.get(snake.getFromTileId());
        Pane toPane = tileMap.get(snake.getToTileId());

        if (fromPane == null || toPane == null) continue;

        double startX = fromPane.getLayoutX() + fromPane.getWidth() / 2;
        double startY = fromPane.getLayoutY() + fromPane.getHeight() / 2;
        double endX = toPane.getLayoutX() + toPane.getWidth() / 2;
        double endY = toPane.getLayoutY() + toPane.getHeight() / 2;

        Line snakeLine = new Line(startX, startY, endX, endY);
        snakeLine.setStrokeWidth(5);
        snakeLine.setStroke(Color.CRIMSON); // rød farge
        snakeLine.setOpacity(0.7);

        overlay.getChildren().add(snakeLine);
      }
    });
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
