package edu.ntnu.idi.idatt.model.game;

import java.util.Objects;

public class Token {

  private final String name;
  private final String iconFileName;

  public Token(String name, String imageFileName) {
    this.name = Objects.requireNonNull(name, "Token name cannot be null.");
    this.iconFileName = imageFileName;
  }

  public String getName() {
    return name;
  }

  public String getIconFileName() {
    return iconFileName;
  }

  @Override
  public String toString() {
    return name + (iconFileName != null ? " [" + iconFileName + "]" : "");
  }

}
