package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * A side panel used in the Bestie PointBattles game mode. Displays player list with icons and
 * scores, a roll button, and welcome text. Updates dynamically as player coins/stars change.
 */
public class BestieSidePanel extends VBox {

  private final VBox playerInfoBox = new VBox(10);
  private final Text infoText = new Text("Welcome to Bestie Point Battles!");
  private final Button rollButton = new Button("Roll Dice");
  private final PlayerList playerList;
  private Runnable onRoll;

  /**
   * Constructs the side panel with a roll button and player list.
   *
   * @param game the current {@link BoardGame} instance
   */
  public BestieSidePanel(BoardGame game) {
    setSpacing(15);
    setPadding(new Insets(15));
    setAlignment(Pos.TOP_CENTER);
    setStyle(
        "-fx-background-color: #ffe6f0; -fx-border-color: #ff66b2; -fx-border-radius: 10; -fx-background-radius: 10;");
    setMinWidth(300);

    rollButton.setOnAction(e -> {
      if (onRoll != null) {
        onRoll.run();
      }
    });

    this.playerList = new PlayerList(game.getPlayers());

    Text scoreLabel = new Text("Score:");
    playerInfoBox.setAlignment(Pos.CENTER_LEFT);
    playerInfoBox.setPadding(new Insets(5, 10, 5, 10));
    updatePlayerInfo(game.getPlayers());

    getChildren().addAll(infoText, rollButton, playerList, scoreLabel, playerInfoBox);
  }

  /**
   * Sets a callback that runs when the roll button is clicked.
   *
   * @param callback the action to run on button press
   */
  public void setRollCallback(Runnable callback) {
    this.onRoll = callback;
  }

  /**
   * Updates the player score list shown in the panel.
   *
   * @param players the list of players with updated values
   */
  public void updatePlayerInfo(List<Player> players) {
    playerInfoBox.getChildren().clear();
    for (Player player : players) {
      if (player instanceof BestiePlayer bp) {
        String text = bp.getName() + " — Coins: " + bp.getCoins() + " | Stars: " + bp.getStars();
        playerInfoBox.getChildren().add(new Text(text));
      } else {
        playerInfoBox.getChildren().add(new Text(player.getName()));
      }
    }
  }

  /**
   * Highlights the current player in the player list.
   *
   * @param currentPlayer the player whose turn it is
   */
  public void highlightPlayer(BestiePlayer currentPlayer) {
    playerList.highlightPlayer(currentPlayer);
  }
}
