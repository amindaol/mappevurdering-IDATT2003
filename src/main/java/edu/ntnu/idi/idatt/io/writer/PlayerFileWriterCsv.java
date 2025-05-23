package edu.ntnu.idi.idatt.io.writer;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV-based implementation of {@link PlayerFileWriter} that writes a list of players to a CSV
 * file.
 *
 *  @author Aminda Lunde
 *  @author Ingrid Opheim
 *  @version 1.0
 */
public class PlayerFileWriterCsv implements PlayerFileWriter {

  /**
   * Writes a list of players to a CSV file.
   *
   * @param path    the Path to write the CSV file to
   * @param players the List of Player objects to persist
   * @throws DaoException if an error occurs during the write operation
   */
  @Override
  public void writePlayers(Path path, List<Player> players) throws DaoException {
    try {
      List<String> lines = players.stream()
          .map(this::formatPlayerLine)
          .toList();

      Files.write(path, lines);
    } catch (IOException e) {
      throw new DaoException("Failed to write players to file: " + path, e);
    }
  }

  /**
   * Formats a single Player object into a CSV-compatible string line.
   * The format is: "name,iconFileName"
   *
   * @param player the Player to format
   * @return the formatted line representing the player
   */
  private String formatPlayerLine(Player player) {
    return player.getName() + "," + player.getToken().getIconFileName();
  }
}
