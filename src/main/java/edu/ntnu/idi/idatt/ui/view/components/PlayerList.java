package edu.ntnu.idi.idatt.ui.view.components;

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
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

public class PlayerList extends VBox {

  private final Map<Player, HBox> playerBoxMap = new HashMap<>();

  public PlayerList(List<Player> players) {
    super(10);
    setAlignment(Pos.CENTER);
    setPadding(new Insets(10));

    for (Player player : players) {
      Token token = player.getToken();
      String iconPath = "/icons/players/" + token.getIconFileName();

      Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
      PlayerIcon playerIcon = new PlayerIcon(player.getName(), icon);

      Label playerNameLabel = new Label(player.getName());

      HBox playerBox = new HBox(10, playerIcon, playerNameLabel);
      playerBox.setAlignment(Pos.CENTER_LEFT);
      playerBox.setPadding(new Insets(5));
      playerBox.getStyleClass().add("playerlist-player-box");

      playerBoxMap.put(player, playerBox);
      getChildren().add(playerBox);
    }
  }

  public void highlightPlayer(Player currentPlayer) {
    for (Map.Entry<Player, HBox> entry : playerBoxMap.entrySet()) {
      HBox playerBox = entry.getValue();
      Label label = (Label) entry.getValue().getChildren().get(1);
      if (entry.getKey().equals(currentPlayer)) {
        playerBox.getStyleClass().add("highlighted-player-box");
        label.getStyleClass().add("highlighted-player");
      } else {
        playerBox.getStyleClass().remove("highlighted-player-box");
        label.getStyleClass().remove("highlighted-player");
      }
    }
  }
}
