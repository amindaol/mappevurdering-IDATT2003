package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.game.Token;

public class TokenFactory {

  public static Token fromIcon(String iconName) {
    if (iconName == null || iconName.isBlank()) {
      throw new IllegalArgumentException("Icon name cannot be null or blank");
    }

    String capitalized = iconName.substring(0, 1).toUpperCase() + iconName.substring(1).toLowerCase();
    return new Token(capitalized, iconName + ".png");
  }

}
