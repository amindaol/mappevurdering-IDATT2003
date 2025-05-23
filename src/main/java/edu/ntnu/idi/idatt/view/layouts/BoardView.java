package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidBoardConfigurationException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TileNotFoundException;
import edu.ntnu.idi.idatt.view.components.DieContainer;
import edu.ntnu.idi.idatt.view.components.LaddersBoard;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.components.PlayerList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * A visual layout for the Love & Ladders game board. Combines the {@link LaddersBoard}, dice
 * display, and a roll button into a scrollable layout. Responsible for placing and moving player
 * icons and updating the dice.
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
  private final VBox playerListContainer = new VBox();
  private PlayerList playerList;
  private Runnable onRoll;

  /**
   * Creates a BoardView with the given board dimensions and dice count.
   *
   * @param rows       the number of rows in the board
   * @param cols       the number of columns in the board
   * @param diceAmount the number of dice to display
   */
  public BoardView(int rows, int cols, int diceAmount) {
    this.getStyleClass().add("board-root");

    this.board = new LaddersBoard(rows, cols);
    this.dieContainer = new DieContainer(diceAmount);
    rollDiceButton.getStyleClass().add("roll-dice-button");
    HBox rollButtonWrapper = new HBox(rollDiceButton);
    rollButtonWrapper.setAlignment(Pos.CENTER_LEFT);

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

    playerListContainer.setAlignment(Pos.CENTER);

    VBox diceBox = new VBox(playerListContainer, dieContainer, rollButtonWrapper);
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
   * @param icon       the visual icon component for the player
   * @param tileId     the ID of the tile to place the icon on
   * @throws TileNotFoundException if the specified tile ID does not exist on the board
   */
  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    playerIcons.put(playerName, icon);
    Pane tile = board.getTile(tileId);
    if (tile == null) {
      throw new TileNotFoundException("Tile ID " + tileId + " not found on the board.");
    }
    tile.getChildren().add(icon);
  }

  /**
   * Moves a player icon to a new tile. Removes it from the previous tile, if present.
   *
   * @param playerName the name of the player
   * @param tileId     the target tile ID
   * @throws TileNotFoundException if the specified tile ID does not exist on the board
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
    if (newTile == null) {
      throw new TileNotFoundException("Tile ID " + tileId + " not found on the board.");
    }
    newTile.getChildren().add(icon);
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
    for (int v : values) {
      dots.add(v);
    }
    dieContainer.setDotsAllDice(dots);
  }

  /**
   * Draws the game board based on the provided board model. Includes tiles, ladders, and snakes.
   *
   * @param boardModel the board to visualize
   */
  public void drawBoard(Board boardModel) {
    if (boardModel == null) {
      throw new InvalidBoardConfigurationException("Board configuration is invalid.");
    }

    System.out.println("Ladders from board model: " + boardModel.getLadders());

    for (Tile tile : boardModel.getTiles()) {
      board.getTile(tile.getTileId());
    }

    board.drawLadders(boardModel.getLadders());
    board.drawSnakes(boardModel.getSnakes());
  }

  /**
   * Initializes the player list by creating a new {@link PlayerList} and populating it with the given players.
   * The list is then set as the content in the {@link VBox} playerListContainer, displaying the players in the UI.
   *
   * @param players the list of players to initialize the player list with
   */
  public void initializePlayerList(List<Player> players) {
    playerList = new PlayerList(players);
    playerListContainer.getChildren().setAll(playerList);
  }

  /**
   * Updates the current player list by highlighting the given player in the UI.
   * If the player list is initialized, the specified player is visually highlighted in the list.
   *
   * @param player the player to highlight in the current player list
   */
  public void updateCurrentPlayerList(Player player) {
    if (playerList != null) {
      playerList.highlightPlayer(player);
    }
  }
}
