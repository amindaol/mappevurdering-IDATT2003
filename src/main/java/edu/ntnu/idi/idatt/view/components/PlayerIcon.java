package edu.ntnu.idi.idatt.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A small image component representing a player on the game board.
 * Wraps an {@link ImageView} with a player name for identification.
 * Used for rendering player tokens during gameplay.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PlayerIcon extends ImageView {

  private final String playerName;

  /**
   * Creates a new player icon with the given image and name.
   *
   * @param playerName the name of the player
   * @param icon the image used as the player token
   */
  public PlayerIcon(String playerName, Image icon) {
    super(icon);
    this.playerName = playerName;

    setFitWidth(30);
    setFitHeight(30);
    setPreserveRatio(true);
  }
}
