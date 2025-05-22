package edu.ntnu.idi.idatt.util;

public class BoardGameUtils {

  public static int toJavaFXY(int row, int totalRows) {
    return totalRows - 1 - row;
  }
}
