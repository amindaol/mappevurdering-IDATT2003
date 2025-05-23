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
   * @throws IllegalArgumentException if playerName is null or empty
   * @throws NullPointerException if icon is null
   */
  public PlayerIcon(String playerName, Image icon) {
    super(icon);

    if (playerName == null || playerName.isEmpty()) {
      throw new IllegalArgumentException("Player name cannot be null or empty.");
    }

    if (icon == null) {
      throw new NullPointerException("Icon image cannot be null.");
    }

    this.playerName = playerName;

    setFitWidth(30);
    setFitHeight(30);
    setPreserveRatio(true);
  }
}
