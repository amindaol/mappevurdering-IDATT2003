package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.action.JumpToTileAction;
import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;
import edu.ntnu.idi.idatt.model.action.SkipNextTurnAction;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Tile;

public class BoardFactory {

  private BoardFactory() {

  }

  public static Board createDefaultBoard() {
    Board board = new Board();
    for (int i = 1; i <= 30; i++) {
      board.addTile(new Tile(i));
    }
    for (int i = 1; i < 90; i++) {
      board.getTile(i).setNextTile(board.getTile(i + 1));
    }
    board.setStartTile(board.getTile(1));
    return board;
  }

  public static Board createLoveAndLaddersBoard() {
    Board board = createDefaultBoard();

    board.getTile(3).setAction(new JumpToTileAction(board.getTile(14)));  // ladder
    board.getTile(8).setAction(new JumpToTileAction(board.getTile(4)));   // snake
    board.getTile(19).setAction(new JumpToTileAction(board.getTile(27))); // ladder
    board.getTile(25).setAction(new JumpToTileAction(board.getTile(17))); // snake

    return board;
  }

  public static Board createBestiePointBattlesBoard() {
    Board board = createDefaultBoard();

    board.getTile(5).setAction(new ModifyPointsAction(+3));
    board.getTile(10).setAction(new ModifyPointsAction(-2));
    board.getTile(15).setAction(new ModifyPointsAction(+5));
    board.getTile(20).setAction(new SkipNextTurnAction());

    return board;
  }
}
