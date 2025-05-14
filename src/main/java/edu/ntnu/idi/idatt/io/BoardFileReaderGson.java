package edu.ntnu.idi.idatt.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.model.action.AddPointsAction;
import edu.ntnu.idi.idatt.model.action.GoToStartAction;
import edu.ntnu.idi.idatt.model.action.LadderAction;
import edu.ntnu.idi.idatt.model.action.RemovePointsAction;
import edu.ntnu.idi.idatt.model.action.SkipNextTurnAction;
import edu.ntnu.idi.idatt.model.action.SnakeAction;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidJsonFormatException;
import java.io.*;

/**
 * Gson-based implementation of {@link BoardFileReader} that reads a board
 * configuration with rows, cols, and specialTiles (ladders, snakes, etc.).
 */
public class BoardFileReaderGson implements BoardFileReader {

  @Override
  public Board readBoard(InputStream stream) throws DaoException {
    try (Reader reader = new InputStreamReader(stream)) {
      JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
      return parseBoard(root);
    } catch (Exception e) {
      throw new DaoException("Failed to load board from resource stream", e);
    }
  }

  private Board parseBoard(JsonObject root) {
    int rows = root.get("rows").getAsInt();
    int cols = root.get("cols").getAsInt();
    int total = rows * cols;

    Board board = new Board();
    for (int id = 1; id <= total; id++) {
      board.addTile(new Tile(id));
    }

    for (int id = 1; id < total; id++) {
      Tile current = board.getTile(id);
      Tile next = board.getTile(id + 1);
      current.setNextTile(next);
    }

    JsonArray specials = root.getAsJsonArray("specialTiles");
    if (specials != null) {
      for (JsonElement el : specials) {
        JsonObject spec = el.getAsJsonObject();
        int tileId = spec.get("id").getAsInt();
        JsonObject actionObj = spec.getAsJsonObject("action");
        String type = actionObj.get("type").getAsString();

        switch (type) {
          case "Ladder" -> {
            int dest = actionObj.get("destinationTileId").getAsInt();
            board.getTile(tileId).setLandAction(new LadderAction(dest, "Ladder to " + dest));
          }
          case "Snake" -> {
            int dest = actionObj.get("destinationTileId").getAsInt();
            board.getTile(tileId).setLandAction(new SnakeAction(dest));
          }
          case "GoToStart" -> board.getTile(tileId).setLandAction(new GoToStartAction());
          case "SkipNextTurn" -> board.getTile(tileId).setLandAction(new SkipNextTurnAction());
          case "AddPoints" -> {
            int points = actionObj.get("points").getAsInt();
            board.getTile(tileId).setLandAction(new AddPointsAction(points));
          }
          case "RemovePoints" -> {
            int points = actionObj.get("points").getAsInt();
            board.getTile(tileId).setLandAction(new RemovePointsAction(points));
          }
          default -> throw new InvalidJsonFormatException("Unknown action type: " + type, null);
        }
      }
    }

    return board;
  }
}
