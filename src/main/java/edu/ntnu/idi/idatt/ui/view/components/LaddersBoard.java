package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.model.game.Ladder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class LaddersBoard {

  private final GridPane grid;
  private final Map<Integer, Pane> tileMap = new HashMap<>();
  private static final double TILE_SIZE = 80;
  private final int rows;
  private final int cols;


  public LaddersBoard(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new GridPane();
    grid.setAlignment(Pos.CENTER);

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
    if (ladders == null || ladders.isEmpty()) return;

    Platform.runLater(() -> {
      Image ladderImage = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream("/icons/ladder.png")
      ));

      for (Ladder ladder : ladders) {
        Pane fromPane = tileMap.get(ladder.getFromTileId());
        Pane toPane = tileMap.get(ladder.getToTileId());

        if (fromPane == null || toPane == null) continue;

        Bounds fromBounds = fromPane.localToScene(fromPane.getBoundsInLocal());
        Bounds toBounds = toPane.localToScene(toPane.getBoundsInLocal());

        double startX = fromBounds.getMinX() + fromBounds.getWidth() / 2;
        double startY = fromBounds.getMinY() + fromBounds.getHeight() / 2;
        double endX = toBounds.getMinX() + toBounds.getWidth() / 2;
        double endY = toBounds.getMinY() + toBounds.getHeight() / 2;

        double angle = Math.toDegrees(Math.atan2(endY - startY, endX - startX));
        double distance = Math.hypot(endX - startX, endY - startY);

        ImageView ladderView = new ImageView(ladderImage);
        ladderView.setFitHeight(distance);
        ladderView.setFitWidth(20);
        ladderView.setPreserveRatio(false);

        ladderView.setLayoutX(startX);
        ladderView.setLayoutY(startY);
        ladderView.setRotate(angle);

        grid.getChildren().add(ladderView);
      }
    });
  }



  public Pane getTile(int tileId) {
    return tileMap.get(tileId);
  }

  public GridPane getGrid() {
    return grid;
  }
}
