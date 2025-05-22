package edu.ntnu.idi.idatt.view.layouts;


import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.view.components.BestieBoard;
import edu.ntnu.idi.idatt.view.components.BestieSidePanel;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BestieBattlesView extends BorderPane {

  private final BestieBoard board;
  private final BestieSidePanel sidePanel;
  private final BoardGame game;



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

  public BestieBoard getBoard() {
    return board;
  }

  public BestieSidePanel getSidePanel() {
    return sidePanel;
  }

  public void setupBoard() {
    board.drawTiles(); // eller hva enn din `BestieBoard` bruker for å tegne rutenett
  }

  public void setRollCallback(Runnable onRoll) {
    sidePanel.setRollCallback(onRoll);
  }

  public void updatePlayerInfo() {
    sidePanel.updatePlayerInfo(game.getPlayers());
  }

  public void placePlayerIcon(String name, PlayerIcon icon, int tileId) {
    board.placePlayerIcon(name, icon, tileId);
  }

  public void movePlayerIcon(String name, PlayerIcon icon, int tileId) {
    board.movePlayerIcon(name, icon, tileId);
  }
}
