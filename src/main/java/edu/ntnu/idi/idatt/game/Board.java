package edu.ntnu.idi.idatt.game;

import java.util.HashMap;
import java.util.Map;

public class Board {

  private Map<Integer, Tile> tiles;

  public Board() {
    this.tiles = new HashMap<>();
  }

  public void addTile(Tile tile) {
    tiles.put(tile.getTileId(), tile);
  }

  public Tile getTile(int tileId) {
    return tiles.get(tileId);
  }

  public int size() {
    return tiles.size();
  }
}
