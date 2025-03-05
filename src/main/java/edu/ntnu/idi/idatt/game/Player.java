package edu.ntnu.idi.idatt.game;

/**
 * Represents a player in a board game.
 */
public class Player {

  private String name;
  Tile currentTile;
  BoardGame boardGame;

  /**
   * Constructs a player with a name and a reference to the board game it is part of.
   * Player should also know what tile it is currently on.
   *
   * @param name name of the player
   * @param boardGame reference to which board game the player is part of
   */
  public Player(String name, BoardGame boardGame) {̉̉
    this.name = name;
    this.currentTile = null;
    this.boardGame = boardGame;
  }


  /**
   * Places the player on a certain tile.
   */
  public void placeOnTile(Tile tile) {
    this.currentTile = tile;
  }

  // TODO: Implement a move method that moves player x amount of steps

}
