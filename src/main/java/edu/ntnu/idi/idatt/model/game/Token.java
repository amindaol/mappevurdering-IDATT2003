package edu.ntnu.idi.idatt.model.game;

import java.util.Objects;

/**
 * Represents a token in the game. A token is the piece representing the player on the board. Each
 * token has a name and an optional icon file name.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Token {

  private final String name;
  private final String iconFileName;

  /**
   * Constructs a Token with the specified name and icon file name.
   *
   * @param name          the name of the token
   * @param imageFileName the icon file name of the token
   * @throws NullPointerException if {@code name} is {@code null}
   */
  public Token(String name, String imageFileName) {
    this.name = Objects.requireNonNull(name, "Token name cannot be null.");
    this.iconFileName = imageFileName;
  }

  /**
   * Returns the name of the token.
   *
   * @return the name of the token
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the icon file name of the token.
   *
   * @return the icon file name of the token
   */
  public String getIconFileName() {
    return iconFileName;
  }

  /**
   * Returns a string representation of the token.
   *
   * @return a string representation of the token
   */
  @Override
  public String toString() {
    return name + (iconFileName != null ? " [" + iconFileName + "]" : "");
  }

}
