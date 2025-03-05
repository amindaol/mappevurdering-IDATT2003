package edu.ntnu.idi.idatt.game;

public class Player {

  private String name;
  Tile currentTile;
  BoardGame boardGame;

  public Player(String name, BoardGame boardGame) {
    this.name = name;
    this.currentTile = null;
    this.boardGame = boardGame;
  }

  
}
