package edu.ntnu.idi.idatt.io.reader;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads players from a CSV file (format: name,token) into a List of {@link Player}. Skips the first
 * (header) row automatically.
 */
public class PlayerFileReaderCsv implements PlayerFileReader {

  @Override
  public List<Player> readPlayers(Path path) throws DaoException {
    List<Player> players = new ArrayList<>();

    try {
      List<String> lines = Files.readAllLines(path);
      for (int i = 1; i < lines.size(); i++) { // Skip header
        String line = lines.get(i).trim();
        if (line.isEmpty()) {
          continue;
        }
        players.add(parsePlayer(line));
      }
      return players;
    } catch (IOException | IllegalArgumentException e) {
      throw new DaoException("Failed to read players from file: " + path, e);
    }
  }

  /**
   * Parses a single line of CSV into a Player.
   *
   * @param line CSV line in format "name,iconFile"
   * @return Player instance
   * @throws IllegalArgumentException if the line is malformed
   */
  private Player parsePlayer(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("Empty or null CSV line.");
    }

    String[] parts = line.split(",");

    if (parts.length < 3) {
      throw new IllegalArgumentException("Malformed player line: " + line);
    }

    String name = parts[0].trim();
    String birthday = parts[1].trim();
    String icon = parts[2].trim();

    if (name.isEmpty() || icon.isEmpty() || birthday.isEmpty()) {
      throw new IllegalArgumentException("Empty player name, icon, or birthday: " + line);
    }

    LocalDate birthdayDate;
    try {
      birthdayDate = LocalDate.parse(birthday);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid date format for birthday: " + birthday, e);
    }

    Token token = new Token(name, icon);
    return new Player(name, token, birthdayDate);
  }
}
