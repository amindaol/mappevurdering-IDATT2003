package edu.ntnu.idi.idatt.config;

public enum LadderBoardVariant {
  BOARD1("ladderBoard1.json"),
  BOARD2("ladderBoard2.json"),
  BOARD3("ladderBoard3.json");

  private final String filename;

  LadderBoardVariant(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
