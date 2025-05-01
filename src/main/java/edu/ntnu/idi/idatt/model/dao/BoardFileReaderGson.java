package edu.ntnu.idi.idatt.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.action.LadderAction;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidJsonFormatException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gson-based implementation of {@link BoardFileReader}.
 */
public class BoardFileReaderGson implements BoardFileReader {

  /**
   * Reads a {@link Board} object from the configured JSON file. The JSON is expected to contain a
   * "tiles" array defining each tile's properties, including id, nextTile, and optional action
   * definitions (e.g., LadderAction).
   *
   * @return a fully constructed {@link Board} instance with all tiles linked and actions set
   * @throws DaoException               if an I/O error occurs while opening the file
   * @throws InvalidJsonFormatException if the JSON is malformed or missing required elements
   */
  @Override
  public Board readBoard(Path path) throws DaoException {
    try (Reader reader = Files.newBufferedReader(path)) {
      JsonElement rootEl = JsonParser.parseReader(reader);
      if (!rootEl.isJsonObject()) {
        throw new InvalidJsonFormatException("Root element is not a JSON object", null);
      }
      JsonObject root = rootEl.getAsJsonObject();

      JsonArray tilesJson = root.getAsJsonArray("tiles");
      if (tilesJson == null) {
        throw new InvalidJsonFormatException("Missing 'tiles' array in JSON", null);
      }

      Board board = new Board();

      for (JsonElement elem : tilesJson) {
        JsonObject tileObj = elem.getAsJsonObject();
        int id = tileObj.get("id").getAsInt();
        board.addTile(new Tile(id));
      }

      for (JsonElement elem : tilesJson) {
        JsonObject tileObj = elem.getAsJsonObject();
        int id = tileObj.get("id").getAsInt();
        Tile tile = board.getTile(id);

        if (tileObj.has("nextTile")) {
          int nextId = tileObj.get("nextTile").getAsInt();
          tile.setNextTile(board.getTile(nextId));
        }

        if (tileObj.has("action")) {
          JsonObject actionObj = tileObj.getAsJsonObject("action");
          String type = actionObj.get("type").getAsString();
          if ("LadderAction".equals(type)) {
            int dest = actionObj.get("destinationTileId").getAsInt();
            String desc = actionObj.get("description").getAsString();
            tile.setLandAction(new LadderAction(dest, desc));
          }
        }
      }

      return board;

    } catch (IOException e) {
      throw new DaoException("Failed to open board JSON file", e);
    } catch (JsonParseException e) {
      throw new InvalidJsonFormatException("Malformed JSON in board file", e);
    }
  }
}
