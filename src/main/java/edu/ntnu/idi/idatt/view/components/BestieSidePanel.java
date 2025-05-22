package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * A side panel used in the Bestie PointBattles game mode.
 * Displays player scores, a roll button, and welcome text.
 * Updates dynamically as player points change.
 *
 * Used in the board layout for Bestie PointBattles.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieSidePanel extends VBox {

  private final VBox playerInfoBox = new VBox(5);
  private final Text infoText = new Text("Welcome to Bestie Point Battles!");
  private final Button rollButton = new Button("Roll Dice");
  private Runnable onRoll;

  /**
   * Constructs the side panel with a roll button and player score list.
   *
   * @param game the current {@link BoardGame} instance
   */
  public BestieSidePanel(BoardGame game) {
    setSpacing(10);
    setPadding(new Insets(15));
    setAlignment(Pos.TOP_CENTER);
    setStyle("-fx-background-color: #ffe6f0; -fx-border-color: #ff66b2; -fx-border-radius: 10; -fx-background-radius: 10;");
    setMinWidth(200);

    rollButton.setOnAction(e -> {
      if (onRoll != null) onRoll.run();
    });

    Text pointsLabel = new Text("Points:");
    getChildren().addAll(infoText, rollButton, pointsLabel, playerInfoBox);

    updatePlayerInfo(game.getPlayers());
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
   * @param players the list of players with updated point values
   */
  public void updatePlayerInfo(List<Player> players) {
    playerInfoBox.getChildren().clear();
    for (Player player : players) {
      String text = player.getName() + ": " + player.getPoints() + " points";
      playerInfoBox.getChildren().add(new Text(text));
    }
  }
}