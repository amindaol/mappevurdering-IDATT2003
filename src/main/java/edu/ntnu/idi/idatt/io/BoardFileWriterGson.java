package edu.ntnu.idi.idatt.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.model.action.*;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Gson-based implementation of {@link BoardFileWriter}.
 */
public class BoardFileWriterGson implements BoardFileWriter {

  @Override
  public void writeBoard(Path path, Board board) throws DaoException {
    try {
      JsonObject root = new JsonObject();
      JsonArray tilesJson = new JsonArray();

      for (Tile tile : board.getTilesOrdered()) {
        JsonObject tileJson = new JsonObject();
        tileJson.addProperty("id", tile.getTileId());

        if (tile.getNextTile() != null) {
          tileJson.addProperty("nextTile", tile.getNextTile().getTileId());
        }

        if (tile.getAction() != null) {
          tileJson.add("action", serializeAction(tile.getAction()));
        }

        tilesJson.add(tileJson);
      }

      root.add("tiles", tilesJson);
      Files.writeString(path, root.toString());

    } catch (IOException e) {
      throw new DaoException("Failed to write board to JSON: " + path, e);
    }
  }

  private JsonObject serializeAction(TileAction action) {
    JsonObject obj = new JsonObject();

    if (action instanceof JumpToTileAction jump) {
      obj.addProperty("type", "LadderAction");
      obj.addProperty("destinationTileId", jump.getDestination().getTileId());
    } else if (action instanceof ModifyPointsAction modify) {
      obj.addProperty("type", "ModifyPointsAction");
      obj.addProperty("points", modify.getPoints());
    } else if (action instanceof SkipNextTurnAction) {
      obj.addProperty("type", "SkipNextTurnAction");
    } else {
      obj.addProperty("type", "UnknownAction");
    }

    return obj;
  }

}
