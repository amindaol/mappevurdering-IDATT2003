package edu.ntnu.idi.idatt.ui.view.layouts;


import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.ui.view.components.TileComponent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BestieBattlesView extends BorderPane {

  private final BoardGame game;
  private final GridPane boardGrid;
  private final Text infoText;
  private final VBox playerInfoBox = new VBox(5);

  public BestieBattlesView(BoardGame game) {
    this.game = game;
    this.boardGrid = new GridPane();
    this.infoText = new Text("Welcome to Bestie Point Battles!");

    setupBoard();

    Button nextTurnButton = new Button("Next Turn");
    nextTurnButton.setOnAction(e -> handleNextTurn());

    Text playersTitle = new Text("Point Overview:");

    VBox rightPanel = new VBox(10, infoText, nextTurnButton);
    rightPanel.setMinWidth(200);

    setCenter(boardGrid);
    setRight(rightPanel);

    updatePlayerInfo();
  }

  private void handleNextTurn() {

  }

  private void setupBoard() {
    boardGrid.setHgap(5);
    boardGrid.setVgap(5);
    var tiles = game.getBoard().getTiles();
    int cols = 10;

    for (int i = 0; i < tiles.size(); i++) {
      Tile tile = tiles.get(i);
      int row = 8 - (i / cols);
      int col = i % cols;

      TileComponent tileComponent = new TileComponent(tile);
      boardGrid.add(tileComponent, col, row);
    }
  }


  private void updatePlayerInfo() {
    playerInfoBox.getChildren().clear();
    for (var player : game.getPlayers()) {
      String text = player.getName() + ": " + player.getPoints() + " poeng";
      playerInfoBox.getChildren().add(new Text(text));
    }
  }
}
