package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.model.action.AddCoinsAction;
import edu.ntnu.idi.idatt.model.action.BuyStarAction;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.view.components.BestieBoard;
import edu.ntnu.idi.idatt.view.components.BestieSidePanel;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.components.PlayerList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * The main game view layout for Bestie PointBattles. Combines the {@link BestieBoard} with a
 * {@link BestieSidePanel} in a scrollable layout. Provides methods to update player info and move
 * icons on the board. Used during gameplay in the Bestie PointBattles mode.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieBattlesView extends BorderPane {

  private final BestieBoard board;
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  private final BestieSidePanel sidePanel;
  private Runnable onRoll;
  private PlayerList playerList;

  /**
   * Constructs a new BestieBattlesView with the given game model.
   *
   * @param game the game model containing the board and player information
   * @throws NullPointerException if the game model is null
   */
  public BestieBattlesView(BoardGame game) {
    this.getStyleClass().add("board-root");
    Board boardModel = game.getBoard();
    this.board = new BestieBoard(boardModel.getRows(), boardModel.getCols());
    this.board.drawBoard(boardModel);
    this.sidePanel = new BestieSidePanel(game);

    highlightCoinTiles(game.getBoard());
    highlightShopTiles(game.getBoard());

    sidePanel.setRollCallback(() -> {
      if (onRoll != null) {
        onRoll.run();
      }
    });

    HBox contentBox = new HBox(board.getBoardWithOverlay(), sidePanel);
    contentBox.getStyleClass().add("board-background");
    contentBox.setSpacing(20);
    contentBox.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(contentBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPannable(true);
    scrollPane.getStyleClass().add("board-background");

    this.setCenter(scrollPane);
  }

  /**
   * gets the root of the layout.
   *
   * @return the root of the layout
   */
  public Parent getRoot() {
    return this;
  }

  /**
   * places a player icon on the board.
   *
   * @param playerName the name of the player
   * @param icon       the icon to place
   * @param tileId     the tile ID to place the icon on
   */
  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    playerIcons.put(playerName, icon);
    Pane tile = board.getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }

  /**
   * moves a player icon to a new tile.
   *
   * @param playerName the name of the player
   * @param tileId     the tile ID to move the icon to
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
   * returns the board component.
   *
   * @return the board component
   */
  public BestieBoard getBoard() {
    return board;
  }

  /**
   * updates the player list with the current player. highlights the current player in the list.
   *
   * @param currentPlayer the current player
   */
  public void updateCurrentPlayerList(Player currentPlayer) {
    if (playerList != null) {
      playerList.highlightPlayer(currentPlayer);
    }
  }

  /**
   * updates the panel with player information.
   *
   * @param players the list of players in the game
   */
  public void updateSidePanel(List<Player> players) {
    if (sidePanel != null) {
      sidePanel.updatePlayerInfo(players);
    }
  }

  /**
   * sets roll callback.
   *
   * @param onRoll the callback to run when the roll button is pressed
   */
  public void setRollCallBack(Runnable onRoll) {
    this.onRoll = onRoll;
    sidePanel.getRollButton().setOnAction(e -> onRoll.run());
  }

  /**
   * shows the dice roll result in the side panel.
   *
   * @param roll the result of the dice roll
   */
  public void showDiceRoll(List<Integer> roll) {
    sidePanel.showDiceRoll(roll);
  }

  /**
   * colours the coin tiles on the board.
   *
   * @param board the board to highlight coin tiles on
   */
  public void highlightCoinTiles(Board board) {
    List<Integer> coinIds = board.getTilesOrdered().stream()
        .filter(t -> t.getAction() instanceof AddCoinsAction)
        .map(Tile::getTileId)
        .toList();

    // tell the board component to style each one
    coinIds.forEach(this.board::drawCoinsTile);
  }

  /**
   * colours the shop tiles on the board.
   *
   * @param board the board to highlight shop tiles on
   */
  private void highlightShopTiles(Board board) {
    List<Integer> shopIds = board.getTilesOrdered().stream()
        .filter(t -> t.getAction() instanceof BuyStarAction)
        .map(Tile::getTileId)
        .toList();

    // tell the board component to style each one
    shopIds.forEach(this.board::drawShopTile);
  }
}