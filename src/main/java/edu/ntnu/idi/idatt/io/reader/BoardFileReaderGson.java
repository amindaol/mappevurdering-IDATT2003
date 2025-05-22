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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<Ladder> ladders = new ArrayList<>();
    List<Ladder> snakes = new ArrayList<>();

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

            if (action.has("destinationTileId")) {
              int destId = action.get("destinationTileId").getAsInt();
              tile.setAction(new JumpToTileAction(tileMap.get(destId)));
              ladders.add(new Ladder(id, destId));
            }
          }
          case "Snake" -> {
            if (action.has("destinationTileId")) {
              int destId = action.get("destinationTileId").getAsInt();
              tile.setAction(new JumpToTileAction(tileMap.get(destId)));
              snakes.add(new Ladder(id, destId));
            }

          }
          case "AddPoints" -> {
            int pts = action.get("points").getAsInt();
            tile.setAction(new ModifyPointsAction(pts));
          }
          case "RemovePoints" -> {
            int pts = action.get("points").getAsInt();
            tile.setAction(new ModifyPointsAction(-pts));
          }
          case "GoToStart" -> {

            if (action.has("destinationTileId")) {
              int destId = action.get("destinationTileId").getAsInt();
              Tile destination = tileMap.get(destId);
              tile.setAction(new JumpToTileAction(destination));
            } else {
              tile.setAction(new JumpToTileAction(tileMap.get(1))); // fallback: gå til start
            }
          }
          case "SkipNextTurn" -> tile.setAction(new SkipNextTurnAction());

          default -> throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }
    }

    board.setLadders(ladders);
    board.setSnakes(snakes);
    board.setStartTile(tileMap.get(1));


    List<Tile> ordered = board.getTilesOrdered();
    for (int i = 0; i < ordered.size() - 1; i++) {
      Tile here = ordered.get(i);
      Tile next = ordered.get(i + 1);
      here.setNextTile(next);
    }



    return board;
  }
}
