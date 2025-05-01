package edu.ntnu.idi.idatt.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.model.game.action.LadderAction;
import edu.ntnu.idi.idatt.model.game.action.SnakeAction;
import edu.ntnu.idi.idatt.model.game.action.GoToStartAction;
import edu.ntnu.idi.idatt.model.game.action.SkipNextTurnAction;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidJsonFormatException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gson-based implementation of {@link BoardFileReader} that reads a board
 * configuration with rows, cols, and specialTiles (ladders, snakes, etc.).
 */
public class BoardFileReaderGson implements BoardFileReader {

  @Override
  public Board readBoard(Path path) throws DaoException {
    try (Reader reader = Files.newBufferedReader(path)) {
      JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

      // Read dimensions
      int rows = root.get("rows").getAsInt();
      int cols = root.get("cols").getAsInt();
      int total = rows * cols;

      // Create board and tiles
      Board board = new Board();
      for (int id = 1; id <= total; id++) {
        board.addTile(new Tile(id));
      }

      // Link sequential nextTile pointers
      for (int id = 1; id < total; id++) {
        Tile current = board.getTile(id);
        Tile next = board.getTile(id + 1);
        current.setNextTile(next);
      }

      // Read special tiles array
      JsonArray specials = root.getAsJsonArray("specialTiles");
      if (specials != null) {
        for (JsonElement el : specials) {
          JsonObject spec = el.getAsJsonObject();
          int tileId = spec.get("id").getAsInt();
          JsonObject actionObj = spec.getAsJsonObject("action");
          String type = actionObj.get("type").getAsString();

          switch (type) {
            case "Ladder": {
              int dest = actionObj.get("destinationTileId").getAsInt();
              board.getTile(tileId).setLandAction(
                  new LadderAction(dest, "Ladder to " + dest)
              );
              break;
            }
            case "Snake": {
              int dest = actionObj.get("destinationTileId").getAsInt();
              board.getTile(tileId).setLandAction(new SnakeAction(dest));
              break;
            }
            case "GoToStart": {
              board.getTile(tileId).setLandAction(new GoToStartAction());
              break;
            }
            case "SkipNextTurn": {
              board.getTile(tileId).setLandAction(new SkipNextTurnAction());
              break;
            }
            default:
              throw new InvalidJsonFormatException(
                  "Unknown action type: " + type,
                  null
              );
          }
        }
      }

      return board;

    } catch (IOException e) {
      throw new DaoException("Failed to open board JSON file", e);
    } catch (JsonParseException | NullPointerException e) {
      throw new InvalidJsonFormatException("Malformed or missing JSON fields", e);
    }
  }
}
