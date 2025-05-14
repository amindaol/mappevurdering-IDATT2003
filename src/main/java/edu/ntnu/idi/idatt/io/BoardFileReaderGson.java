package edu.ntnu.idi.idatt.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.model.action.JumpToTileAction;
import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;
import edu.ntnu.idi.idatt.model.action.SkipNextTurnAction;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidJsonFormatException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Gson-based implementation of {@link BoardFileReader} that reads a board
 * configuration with rows, cols, and specialTiles (ladders, snakes, etc.).
 */
public class BoardFileReaderGson implements BoardFileReader {

  @Override
  public Board readBoard(Path path) throws DaoException {
    try {
      String json = Files.readString(path);
      JsonObject root = JsonParser.parseString(json).getAsJsonObject();
      return parseBoard(root);
    } catch (IOException | IllegalArgumentException | NullPointerException e) {
      throw new DaoException("Failed to load board from file: " + path, e);
    }
  }

  public Board parseBoard(JsonObject root) {
    JsonArray tileArray = root.getAsJsonArray("tiles");
    Map<Integer, Tile> tileMap = new HashMap<>();
    Board board = new Board();

    for (JsonElement tileElem : tileArray) {
      JsonObject tileObj = tileElem.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      Tile tile = new Tile(id);
      board.addTile(tile);
      tileMap.put(id, tile);
    }

    for (JsonElement tileElem : tileArray) {
      JsonObject tileObj = tileElem.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      Tile tile = tileMap.get(id);

      if (tileObj.has("nextTile")) {
        int nextId = tileObj.get("nextTile").getAsInt();
        tile.setNextTile(tileMap.get(nextId));
      }

      if (tileObj.has("action")) {
        JsonObject actionObj = tileObj.getAsJsonObject("action");
        String type = actionObj.get("type").getAsString();

        switch (type) {
          case "LadderAction", "SnakeAction" -> {
            int destId = actionObj.get("destinationTileId").getAsInt();
            tile.setAction(new JumpToTileAction(tileMap.get(destId)));
          }
          case "ModifyPointsAction" -> {
            int points = actionObj.get("points").getAsInt();
            tile.setAction(new ModifyPointsAction(points));
          }
          case "SkipNextTurnAction" -> tile.setAction(new SkipNextTurnAction());
          default -> throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }
    }

    board.setStartTile(tileMap.get(1));
    return board;
  }
}