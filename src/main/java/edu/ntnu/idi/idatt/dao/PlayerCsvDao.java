package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-based implementation of PlayerDao interface. Reads and writes Player data to a plain-text CSV
 * file.
 */
public class PlayerCsvDao implements PlayerDao {

  private final Path filePath;

  /**
   * Constructs a new PlayerCsvDao for the given CSV file path.
   *
   * @param filePath the path to the CSV file storing player data
   */
  public PlayerCsvDao(Path filePath) {
    this.filePath = filePath;
  }


}
