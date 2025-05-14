package edu.ntnu.idi.idatt.model.game;

public class Token {

  private final String name;
  private final String imageFileName;

  public Token(String name, String imageFileName) {
    this.name = name;
    this.imageFileName = imageFileName;
  }

  public String getName() {
    return name;
  }

  public String getImageFileName() {
    return imageFileName;
  }



}
