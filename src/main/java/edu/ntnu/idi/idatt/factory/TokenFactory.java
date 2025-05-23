package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.game.Token;

/**
 * Factory class for creating {@link Token} objects. This class provides a static method to create a
 * {@code token} from an icon name.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class TokenFactory {

  /**
   * Private constructor to prevent instantiation of the factory class.
   */
  private TokenFactory() {

  }

  /**
   * Created a Token object from an icon name.
   *
   * @param iconName the name of the icon (without file extension)
   * @return a new Token with formatted display name and icon path
   * @throws IllegalArgumentException if the icon name is null or blank
   */
  public static Token fromIcon(String iconName) {
    if (iconName == null || iconName.isBlank()) {
      throw new IllegalArgumentException("Icon name cannot be null or blank");
    }

    String capitalized =
        iconName.substring(0, 1).toUpperCase() + iconName.substring(1).toLowerCase();
    return new Token(capitalized, iconName + ".png");
  }

}
