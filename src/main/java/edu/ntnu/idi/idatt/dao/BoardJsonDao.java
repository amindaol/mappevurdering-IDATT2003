package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

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
   * @param jsonFile path to the JSON file storing board configuration
   */
  public BoardJsonDao(Path jsonFile) {
    this.jsonFile = jsonFile;
  }

  @Override
  public Board readBoard() throws DaoException {
    try (Reader reader = Files.newBufferedReader(jsonFile)) {
      JsonElement rootEl = JsonParser.parseReader(reader);
      if (!rootEl.isJsonObject()) {
        throw new InvalidJsonFormatException("Root element is not a JSON object", null);
      }
      JsonObject root = rootEl.getAsJsonObject();
    } catch (IOException e) {
      throw new DaoException("Failed to open board JSON file", e);
    } catch (JsonParseException e) {
      throw new InvalidJsonFormatException("Malformed JSON in board file", e);
    }
    return null;
  }

  @Override
  public void writeBoard(Board board) throws DaoException {
    throw new UnsupportedOperationException("writeBoard not implemented yet");
  }
}
