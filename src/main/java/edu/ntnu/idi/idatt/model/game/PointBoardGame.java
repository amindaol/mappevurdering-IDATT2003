package edu.ntnu.idi.idatt.model.game;

public class PointBoardGame extends BoardGame {

  public PointBoardGame(Board board, Dice dice) {
    super(board, dice);
  }

  @Override
  public boolean gameIsOver() {
    getPlayers().stream()
        .anyMatch(p -> p.getCurrentTile() >= getBoard().getLastTile());
  }

  @Override
  public Player getWinner() {
    return getPlayers().stream()
        .max((p1, p2) -> Integer.compare(p1.getPoints(), p2.getPoints()))
        .orElse(null);
  }

}
