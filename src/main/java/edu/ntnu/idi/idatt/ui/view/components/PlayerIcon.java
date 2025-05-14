package edu.ntnu.idi.idatt.ui.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerIcon extends ImageView {

  private final String playerName;

  public PlayerIcon(String playerName, Image icon) {
    super(icon);
    this.playerName = playerName;

    setFitWidth(30);
    setFitHeight(30);
    setPreserveRatio(true);
  }

  public String getPlayerName() {
    return playerName;
  }
}
