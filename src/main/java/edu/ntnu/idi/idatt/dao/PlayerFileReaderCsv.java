package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads players from a CSV file (format: name,token) into a List of {@link Player}.
 */
public class PlayerFileReaderCsv implements PlayerFileReader {

  /**
   * Reads all players from the CSV file. Each line is expected in format: name,token
   *
   * @return a List of Player objects
   * @throws DaoException if file I/O fails or CSV line format is invalid
   */
  @Override
  public List<Player> readPlayers(Path csvFile) throws DaoException {
    List<Player> players = new ArrayList<>();
    try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",", 2);
        if (parts.length != 2) {
          throw new DaoException("Invalid CSV line: '" + line + "'", null);
        }
        String name = parts[0].trim();
        String token = parts[1].trim();
        players.add(new Player(name, token));
      }
    } catch (IOException e) {
      throw new DaoException("Error reading players", e);
    }
    return players;
  }

}
