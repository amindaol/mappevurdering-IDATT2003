package edu.ntnu.idi.idatt.io.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.model.action.JumpToTileAction;
import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;
import edu.ntnu.idi.idatt.model.action.SkipNextTurnAction;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Ladder;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    int rows = root.get("rows").getAsInt();
    int cols = root.get("cols").getAsInt();

    JsonArray tileArray = root.getAsJsonArray("tiles");
    Map<Integer, Tile> tileMap = new HashMap<>();
    Board board = new Board(rows, cols);

    for (JsonElement tileElem : tileArray) {
      JsonObject tileObj = tileElem.getAsJsonObject();
      int id = tileObj.get("id").getAsInt();
      int row = tileObj.get("row").getAsInt();
      int col = tileObj.get("col").getAsInt();
      Tile tile = new Tile(id, row, col);
      board.addTile(tile);
      tileMap.put(id, tile);
    }

    if (root.has("specialTiles")) {
      JsonArray specials = root.getAsJsonArray("specialTiles");

      List<Ladder> ladders = new ArrayList<>();

      for (JsonElement specialElem : specials) {
        JsonObject obj = specialElem.getAsJsonObject();
        int id = obj.get("id").getAsInt();
        Tile tile = tileMap.get(id);

        JsonObject action = obj.getAsJsonObject("action");
        String type = action.get("type").getAsString();

        switch (type) {
          case "Ladder" -> {
            int destId = action.get("destinationTileId").getAsInt();
            Tile destination = tileMap.get(destId);
            tile.setAction(new JumpToTileAction(destination));
            ladders.add(new Ladder(id, destId));
          }
          case "Snake" -> {
            int destId = action.get("destinationTileId").getAsInt();
            Tile destination = tileMap.get(destId);
            tile.setAction(new JumpToTileAction(destination));
          }
          case "AddPoints" -> {
            int points = action.get("points").getAsInt();
            tile.setAction(new ModifyPointsAction(points));
          }
          case "RemovePoints" -> {
            int points = action.get("points").getAsInt();
            tile.setAction(new ModifyPointsAction(-points));
          }
          case "GoToStart" -> {
            Tile destination = tileMap.get(1);
            tile.setAction(new JumpToTileAction(destination));
          }
          case "SkipNextTurn" -> tile.setAction(new SkipNextTurnAction());
          default -> throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }
      board.setLadders(ladders);
    }

    board.setStartTile(tileMap.get(1));
    return board;
  }
}