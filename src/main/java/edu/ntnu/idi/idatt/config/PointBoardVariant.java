package edu.ntnu.idi.idatt.config;

public enum PointBoardVariant {
  BOARD1("pointBoard1.json"),
  BOARD2("pointBoard2.json"),
  BOARD3("pointBoard3.json");

  private final String filename;

  PointBoardVariant(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
