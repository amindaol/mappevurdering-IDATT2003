package edu.ntnu.idi.idatt.io.reader;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidPlayerFormatException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads players from a CSV file (format: name,token) into a List of {@link Player}. Skips the first
 * (header) row automatically.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
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
   * @throws InvalidPlayerFormatException if the line is malformed
   */
  private Player parsePlayer(String line) {
    if (line == null || line.isBlank()) {
      throw new InvalidPlayerFormatException("Empty or null CSV line.");
    }

    String[] parts = line.split(",");

    if (parts.length < 3) {
      throw new InvalidPlayerFormatException("Malformed player line: " + line);
    }

    String name = parts[0].trim();
    String birthday = parts[1].trim();
    String icon = parts[2].trim();

    if (name.isEmpty() || icon.isEmpty() || birthday.isEmpty()) {
      throw new InvalidPlayerFormatException("Empty player name, icon, or birthday: " + line);
    }

    LocalDate birthdayDate;
    try {
      birthdayDate = LocalDate.parse(birthday);
    } catch (Exception e) {
      throw new InvalidPlayerFormatException("Invalid date format for birthday: " + birthday, e);
    }

    Token token = new Token(name, icon);
    return new Player(name, token, birthdayDate);
  }
}
