package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.view.components.DieContainer;
import edu.ntnu.idi.idatt.view.components.LaddersBoard;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class BoardView extends BorderPane {

  private final LaddersBoard board;
  private final DieContainer dieContainer;
  private final Button rollDiceButton = new Button("Roll Dice");
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  ;
  private Runnable onRoll;
  private Consumer<int[]> onDiceRolled;

  public BoardView(int rows, int cols, int diceAmount) {
    this.getStyleClass().add("board-root");

    this.board = new LaddersBoard(rows, cols);
    this.dieContainer = new DieContainer(diceAmount);

    int startTileId = 3;
    int endTileId = 22;

    Pane start = board.getTile(startTileId);
    Pane end = board.getTile(endTileId);

    Line ladderLine = new Line();

    ladderLine.startXProperty().bind(start.layoutXProperty().add(start.widthProperty().divide(2)));
    ladderLine.startYProperty().bind(start.layoutYProperty().add(start.heightProperty().divide(2)));
    ladderLine.endXProperty().bind(end.layoutXProperty().add(end.widthProperty().divide(2)));
    ladderLine.endYProperty().bind(end.layoutYProperty().add(end.heightProperty().divide(2)));

    ladderLine.setStyle("-fx-stroke: green; -fx-stroke-width: 4;");


    VBox diceBox = new VBox(dieContainer, rollDiceButton);
    diceBox.setAlignment(Pos.CENTER);
    diceBox.setSpacing(10);

    HBox contentBox = new HBox(board.getBoardWithOverlay(), diceBox);
    contentBox.getStyleClass().add("board-background");
    contentBox.setSpacing(20);
    contentBox.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(contentBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPannable(true);
    scrollPane.getStyleClass().add("board-background");

    this.setCenter(scrollPane);

    configureRollButton();
  }

  public Parent getRoot() {
    return this;
  }

  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    playerIcons.put(playerName, icon);
    Pane tile = board.getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }

  public void movePlayerIcon(String playerName, int tileId) {
    PlayerIcon icon = playerIcons.get(playerName);
    if (icon == null) {
      return;
    }
    if (icon.getParent() instanceof Pane oldTile) {
      oldTile.getChildren().remove(icon);
    }
    Pane newTile = board.getTile(tileId);
    if (newTile != null) {
      newTile.getChildren().add(icon);
    }
  }

  public void configureRollButton() {
    rollDiceButton.setOnAction(e -> {
      if (onRoll != null) {
        onRoll.run();
      }
    });
  }

  public void setRollCallback(Runnable onRoll) {
    this.onRoll = onRoll;
  }

  public void showDiceRoll(List<Integer> values) {
    List<Integer> dots = new ArrayList<>();
    for (int v : values) dots.add(v);
    dieContainer.setDotsAllDice(dots);
  }

  public void drawBoard(Board boardModel) {
    System.out.println("Ladders from board model: " + boardModel.getLadders());

    for (Tile tile : boardModel.getTiles()) {
      board.getTile(tile.getTileId());
    }

      board.drawLadders(boardModel.getLadders());
      board.drawSnakes(boardModel.getSnakes());
  }
}
