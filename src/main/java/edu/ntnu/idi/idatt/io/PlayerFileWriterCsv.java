package edu.ntnu.idi.idatt.io;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV-based implementation of {@link PlayerFileWriter} that writes a list of players to a CSV
 * file.
 */
public class PlayerFileWriterCsv implements PlayerFileWriter {

  /**
   * Writes the list of players to the CSV file, one player per line in format: name,token.
   *
   * @param csvFile path to the CSV file where players will be written
   * @param players the List of Player objects to write
   * @throws DaoException if file I/O fails during writing
   */
  @Override
  public void writePlayers(Path csvFile, List<Player> players) throws DaoException {
    try (BufferedWriter writer = Files.newBufferedWriter(csvFile)) {
      for (Player p : players) {
        writer.write(p.getName() + "," + p.getToken());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new DaoException("Error writing players", e);
    }
  }
}
