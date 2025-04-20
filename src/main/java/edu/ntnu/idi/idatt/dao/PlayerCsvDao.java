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

  /**
   * Reads all players from the CSV file. Each line is expected in format: name,token
   *
   * @return a List of Player objects
   * @throws DaoException if file I/O fails or CSV line format is invalid
   */
  @Override
  public List<Player> readPlayers() throws DaoException {
    List<Player> players = new ArrayList<>();
    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
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

  /**
   * Writes the list of players to the CSV file, one player per line in format: name,token
   *
   * @param players the List of Player objects to write
   * @throws DaoException if file I/O fails during writing
   */
  @Override
  public void writePlayers(List<Player> players) throws DaoException {
    try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
      for (Player p : players) {
        writer.write(p.getName() + "," + p.getToken());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new DaoException("Error writing players", e);
    }
  }
}
