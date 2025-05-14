package edu.ntnu.idi.idatt.io;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV-based implementation of {@link PlayerFileWriter} that writes a list of players to a CSV
 * file.
 */
public class PlayerFileWriterCsv implements PlayerFileWriter {

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

  private String formatPlayerLine(Player player) {
    return player.getName() + "," + player.getToken().getIconFileName();
  }
}
