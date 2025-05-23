package edu.ntnu.idi.idatt.io.writer;

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
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BoardFileWriterGson implements BoardFileWriter {

  /**
   * Serializes the given Board and writes it to the given file path.
   *
   * @param path  the Path to write the JSON (or other) file to
   * @param board the Board instance to serialize
   * @throws DaoException on I/O error
   */
  @Override
  public void writeBoard(Path path, Board board) throws DaoException {
    try {
      JsonObject root = new JsonObject();
      JsonArray tilesJson = new JsonArray();

      for (Tile tile : board.getTilesOrdered()) {
        JsonObject tileJson = new JsonObject();
        tileJson.addProperty("id", tile.getTileId());
        tileJson.addProperty("row", tile.getRow());
        tileJson.addProperty("col", tile.getCol());

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

  /**
   * Converts a TileAction into a JsonObject based on its type.
   *
   * @param action the action to serialize
   * @return a JsonObject representing the action
   * @throws DaoException if the action is unknown
   */
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
      throw new DaoException("Unknown action: " + action);
    }

    return obj;
  }

}
