package edu.ntnu.idi.idatt.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.LadderAction;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.ExceptionHandling.DaoException;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Gson-based implementation of {@link BoardFileWriter}.
 */
public class BoardFileWriterGson implements BoardFileWriter {

  /**
   * Writes the given {@link Board} to the specified JSON file.
   *
   * @param path  the Path to write the JSON to
   * @param board the Board instance to serialize
   * @throws DaoException if an I/O error occurs during writing
   */
  @Override
  public void writeBoard(Path path, Board board) throws DaoException {

    JsonObject root = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    int total = board.size();

    for (int id = 1; id <= total; id++) {
      Tile tile = board.getTile(id);
      JsonObject tileObj = getJsonObject(tile);
      tilesArray.add(tileObj);
    }
    root.add("tiles", tilesArray);

    try (Writer writer = Files.newBufferedWriter(path)) {
      writer.write(root.toString());
    } catch (IOException e) {
      throw new DaoException("Failed to write board JSON file", e);
    }
  }

  private static JsonObject getJsonObject(Tile tile) {
    JsonObject tileObj = new JsonObject();
    tileObj.addProperty("id", tile.getTileId());
    if (tile.getNextTile() != null) {
      tileObj.addProperty("nextTile", tile.getNextTile().getTileId());
    }
    if (tile.getLandAction() instanceof LadderAction ladderAction) {
      JsonObject actionObj = new JsonObject();
      actionObj.addProperty("type", "LadderAction");
      actionObj.addProperty("destinationTileId", ladderAction.getDestinationTileId());
      actionObj.addProperty("description", ladderAction.getDescription());
      tileObj.add("action", actionObj);
    }
    return tileObj;
  }
}
