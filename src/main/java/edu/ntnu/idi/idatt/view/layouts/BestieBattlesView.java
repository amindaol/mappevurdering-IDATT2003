package edu.ntnu.idi.idatt.view.layouts;


import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.view.components.BestieBoard;
import edu.ntnu.idi.idatt.view.components.BestieSidePanel;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The main game view layout for Bestie PointBattles.
 * Combines the {@link BestieBoard} with a {@link BestieSidePanel} in a scrollable layout.
 * Provides methods to update player info and move icons on the board.
 *
 * Used during gameplay in the Bestie PointBattles mode.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieBattlesView extends BorderPane {

  private final BestieBoard board;
  private final BestieSidePanel sidePanel;
  private final BoardGame game;

  /**
   * Constructs the BestieBattlesView with the given game model.
   *
   * @param game the current game instance
   */
  public BestieBattlesView(BoardGame game) {
    this.game = game;
    this.getStyleClass().add("board-root");

    this.board = new BestieBoard(game.getBoard().getTilesOrdered(),
        game.getBoard().getRows(),
        game.getBoard().getCols());
    this.sidePanel = new BestieSidePanel(game);

    HBox contentBox = new HBox(board.getBoardWithOverlay(), sidePanel);
    contentBox.setSpacing(20);
    contentBox.setAlignment(Pos.CENTER);
    contentBox.getStyleClass().add("board-background");

    ScrollPane scrollPane = new ScrollPane(contentBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPannable(true);
    scrollPane.getStyleClass().add("board-background");

    this.setCenter(scrollPane);
  }

  /**
   * Returns the board component used in this view.
   *
   * @return the BestieBoard instance
   */
  public BestieBoard getBoard() {
    return board;
  }

  /**
   * Initializes and draws the board tiles.
   */
  public void setupBoard() {
    board.drawTiles();
  }

  /**
   * Sets the callback to trigger when the roll button is clicked.
   *
   * @param onRoll the action to run
   */
  public void setRollCallback(Runnable onRoll) {
    sidePanel.setRollCallback(onRoll);
  }

  /**
   * Updates the side panel with current player scores.
   */
  public void updatePlayerInfo() {
    sidePanel.updatePlayerInfo(game.getPlayers());
  }

  /**
   * Places a player icon on a specific tile.
   *
   * @param name the player name
   * @param icon the visual icon
   * @param tileId the tile to place the icon on
   */
  public void placePlayerIcon(String name, PlayerIcon icon, int tileId) {
    board.placePlayerIcon(name, icon, tileId);
  }

  /**
   * Moves a player icon to a new tile.
   *
   * @param name the player name
   * @param icon the visual icon
   * @param tileId the target tile ID
   */
  public void movePlayerIcon(String name, PlayerIcon icon, int tileId) {
    board.movePlayerIcon(name, icon, tileId);
  }
}
