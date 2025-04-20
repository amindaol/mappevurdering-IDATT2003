package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.dao.DaoException;
import edu.ntnu.idi.idatt.dao.InvalidJsonFormatException;
import edu.ntnu.idi.idatt.dao.BoardDao;
import edu.ntnu.idi.idatt.game.Board;
import edu.ntnu.idi.idatt.game.Tile;
import edu.ntnu.idi.idatt.game.LadderAction;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


/**
 * JSON-based implementation of {@link BoardDao} using Gson.
 */
public class BoardJsonDao implements BoardDao {

  private final Path jsonFile;

  /**
   * Constructs a BoardJsonDao for reading/writing boards in JSON.
   *
   * @param jsonFile path to the JSON file storing board configuration
   */
  public BoardJsonDao(Path jsonFile) {
    this.jsonFile = jsonFile;
  }

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
  public Board readBoard() throws DaoException {
    try (Reader reader = Files.newBufferedReader(jsonFile)) {
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

  /**
   * Writes the given {@link Board} to the JSON file. The board is serialized with a "tiles" array
   * containing each tile's id, nextTile, and optional action.
   *
   * @param board the Board instance to serialize
   * @throws DaoException if an I/O error occurs during writing
   */
  @Override
  public void writeBoard(Board board) throws DaoException {

    JsonObject root = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    int total = board.size();

    for (int id = 1; id <= total; id++) {
      Tile tile = board.getTile(id);
      JsonObject tileObj = new JsonObject();
      tileObj.addProperty("id", tile.getTileId());
      if (tile.getNextTile() != null) {
        tileObj.addProperty("nextTile", tile.getNextTile().getTileId());
      }
      if (tile.getLandAction() instanceof LadderAction) {
        LadderAction la = (LadderAction) tile.getLandAction();
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "LadderAction");
        actionObj.addProperty("destinationTileId", la.getDestinationTileId());
        actionObj.addProperty("description", la.getDescription());
        tileObj.add("action", actionObj);
      }
      tilesArray.add(tileObj);
    }
    root.add("tiles", tilesArray);

    try (Writer writer = Files.newBufferedWriter(jsonFile)) {
      writer.write(root.toString());
    } catch (IOException e) {
      throw new DaoException("Failed to write board JSON file", e);
    }
  }

}
