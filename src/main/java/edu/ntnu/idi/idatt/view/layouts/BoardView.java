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

/**
 * A visual layout for the Love & Ladders game board.
 * Combines the {@link LaddersBoard}, dice display, and a roll button into a scrollable layout.
 * Responsible for placing and moving player icons and updating the dice.
 *
 * Used as the main game view in the Love & Ladders game mode.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BoardView extends BorderPane {

  private final LaddersBoard board;
  private final DieContainer dieContainer;
  private final Button rollDiceButton = new Button("Roll Dice");
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  ;
  private Runnable onRoll;

  /**
   * Creates a BoardView with the given board dimensions and dice count.
   *
   * @param rows the number of rows in the board
   * @param cols the number of columns in the board
   * @param diceAmount the number of dice to display
   */
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

  /**
   * Returns the root node of this view.
   *
   * @return the view node
   */
  public Parent getRoot() {
    return this;
  }

  /**
   * Places a player icon on the specified tile.
   *
   * @param playerName the name of the player
   * @param icon the visual icon component for the player
   * @param tileId the ID of the tile to place the icon on
   */
  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    playerIcons.put(playerName, icon);
    Pane tile = board.getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }

  /**
   * Moves a player icon to a new tile.
   * Removes it from the previous tile, if present.
   *
   * @param playerName the name of the player
   * @param tileId the target tile ID
   */
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

  /**
   * Configures the dice roll button to trigger a callback when clicked.
   */
  public void configureRollButton() {
    rollDiceButton.setOnAction(e -> {
      if (onRoll != null) {
        onRoll.run();
      }
    });
  }

  /**
   * Sets the callback to run when the roll button is clicked.
   *
   * @param onRoll the callback to trigger
   */
  public void setRollCallback(Runnable onRoll) {
    this.onRoll = onRoll;
  }

  /**
   * Updates the dice display with the values from the last roll.
   *
   * @param values the list of integers representing dice values
   */
  public void showDiceRoll(List<Integer> values) {
    List<Integer> dots = new ArrayList<>();
    for (int v : values) dots.add(v);
    dieContainer.setDotsAllDice(dots);
  }

  /**
   * Draws the game board based on the provided board model.
   * Includes tiles, ladders, and snakes.
   *
   * @param boardModel the board to visualize
   */
  public void drawBoard(Board boardModel) {
    System.out.println("Ladders from board model: " + boardModel.getLadders());

    for (Tile tile : boardModel.getTiles()) {
      board.getTile(tile.getTileId());
    }

      board.drawLadders(boardModel.getLadders());
      board.drawSnakes(boardModel.getSnakes());
  }
}
