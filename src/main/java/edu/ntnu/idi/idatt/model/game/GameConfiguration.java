package edu.ntnu.idi.idatt.model.game;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.javafx.css.parser.Token;
import edu.ntnu.idi.idatt.model.dao.BoardFileReader;
import edu.ntnu.idi.idatt.model.dao.BoardFileWriter;
import edu.ntnu.idi.idatt.model.dao.BoardFileWriterGson;
import edu.ntnu.idi.idatt.model.dao.PlayerFileWriter;
import edu.ntnu.idi.idatt.model.dao.PlayerFileWriterCsv;
import edu.ntnu.idi.idatt.model.game.action.LadderAction;
import edu.ntnu.idi.idatt.model.game.action.TileAction;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameConfiguration {

  private final List<Player> players;
  private final Board board;
  private final int currentPlayerIndex;

  public GameConfiguration(List<Player> players, Board board, int currentPlayerIndex) {
    this.players = players;
    this.board = board;
    this.currentPlayerIndex = currentPlayerIndex;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Board getBoard() {
    return board;
  }

  public int getCurrentPlayerIndex() {
    return currentPlayerIndex;
  }

  public void saveConfiguration() {
    JsonObject config = new JsonObject();

    config.addProperty("currentPlayerIndex", currentPlayerIndex);
    config.addProperty("boardType", board.getClass().getName());

    JsonArray tilesArray = new JsonArray();
    Map<Integer, Tile> tiles = board.getTiles();
    for (Map.Entry<Integer, Tile> entry : tiles.entrySet()) {
      int tileId = entry.getKey();
      Tile tile = entry.getValue();
      TileAction action = tile.getTileAction();

      JsonObject tileObject = new JsonObject();
      tileObject.addProperty("id", tileId);

      if (action != null) {
        JsonObject actionObject = new JsonObject();

        if (action instanceof LadderAction) {
          int destinationTileId = getActionDestinationTileId(action);
          actionObject.addProperty("destinationTileId", destinationTileId);
          actionObject.addProperty("type", "ladder");
        } else {
          actionObject.addProperty("type", "default");
        }

        tileObject.add("action", actionObject);
      }

      tilesArray.add(tileObject);
    }

    config.add("tiles", tilesArray);

    JsonArray playersArray = new JsonArray();
    for (Player player : players) {
      JsonObject playerObject = new JsonObject();
      playerObject.addProperty("name", player.getName());

      JsonArray tokenArray = new JsonArray();
      for (Token token : player.getToken()) {
        JsonObject tokenObject = new JsonObject();
        tokenObject.addProperty("tileId", player.getCurrentTile().getTileId());
        tokenArray.add(tokenObject);
      }
      playerObject.add("token", tokenArray);

      playersArray.add(playerObject);
    }

    BoardFileWriter writer = new BoardFileWriterGson();
    Path pathBoard = Paths.get("src/main/resources/boards");
    try {
      writer.writeBoard(pathBoard, board);
    } catch (DaoException e) {
      e.printStackTrace();
    }

    System.out.println("Game configuration saved to: " + path);
  }

  public void savePlayerList(String path) throws DaoException {
    List<String> playerNames = new ArrayList<>();
    for (Player player : players) {
      playerNames.add(player.getName());
    }
    PlayerFileWriter writer = new PlayerFileWriterCsv();
    Path pathPlayer = Paths.get("src/main/resources/players");
    try {
      writer.writePlayers(pathPlayer, List<String> playerNames);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Player list saved to: " + pathPlayer);
  }

  public GameConfiguration loadConfiguration(String ) throws DaoException {}
}
