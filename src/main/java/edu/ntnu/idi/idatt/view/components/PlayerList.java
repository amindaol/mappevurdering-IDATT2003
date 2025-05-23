package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A visual component that displays a list of players in the game, including their names and player
 * tokens.
 * Each player's name and token icon are displayed in a horizontal box layout.
 * The component allows highlighting of the current player to indicate whose turn it is.
 * The class also provides functionality to update the displayed player list dynamically, as well
 * as to highlight the player currently taking their turn.
 * This component is used in the game setup view and board view to display and manage the player
 * information.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PlayerList extends VBox {

  private final Map<Player, HBox> playerBoxMap = new HashMap<>();

  /**
   * Constructs a PlayerList view with player names and icons.
   *
   * @param players the list of players to display
   * @throws NullPointerException if players list is null
   * @throws IllegalArgumentException if players list contains null or empty players
   */
  public PlayerList(List<Player> players) {
    super(10);
    setAlignment(Pos.CENTER);
    setPadding(new Insets(10));

    if (players == null) {
      throw new NullPointerException("Players list cannot be null.");
    }

    if (players.isEmpty()) {
      throw new IllegalArgumentException("Players list cannot be empty.");
    }

    this.setPrefWidth(300);
    this.setMinWidth(300);
    this.setMaxWidth(300);

    for (Player player : players) {
      if (player == null) {
        throw new IllegalArgumentException("Player cannot be null.");
      }

      Token token = player.getToken();
      if (token == null) {
        throw new IllegalArgumentException("Player token cannot be null.");
      }

      String iconPath = "/icons/players/" + token.getIconFileName();
      Image icon;

      try {
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
      } catch (NullPointerException e) {
        throw new NullPointerException("Icon file not found: " + iconPath);
      }

      PlayerIcon playerIcon = new PlayerIcon(player.getName(), icon);
      Label playerNameLabel = new Label(player.getName());
      playerNameLabel.getStyleClass().add("playerlist-name-label");

      HBox playerBox = new HBox(10, playerIcon, playerNameLabel);
      playerBox.setAlignment(Pos.CENTER_LEFT);
      playerBox.setPadding(new Insets(5));

      playerBoxMap.put(player, playerBox);
      getChildren().add(playerBox);
    }
  }

  /**
   * Highlights the current player's entry in the list.
   *
   * @param currentPlayer the player to highlight
   */
  public void highlightPlayer(Player currentPlayer) {
    for (Map.Entry<Player, HBox> entry : playerBoxMap.entrySet()) {
      HBox playerBox = entry.getValue();
      Label label = (Label) entry.getValue().getChildren().get(1);
      Player player = entry.getKey();

      if (entry.getKey().equals(currentPlayer)) {
        playerBox.getStyleClass().add("highlighted-player-box");
        label.getStyleClass().add("highlighted-player");
        label.setText(player.getName() + "s turn");
      } else {
        playerBox.getStyleClass().remove("highlighted-player-box");
        label.getStyleClass().remove("highlighted-player");
        label.setText(player.getName());
      }
    }
  }
}
