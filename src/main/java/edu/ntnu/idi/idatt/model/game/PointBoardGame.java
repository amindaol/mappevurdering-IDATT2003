package edu.ntnu.idi.idatt.model.game;
import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;


import edu.ntnu.idi.idatt.observer.BoardGameEvent;

public class PointBoardGame extends BoardGame {

  public PointBoardGame(Board board, Dice dice) {
    super(board, dice);
  }

  @Override
  public boolean isFinished() {
    return getPlayers().stream()
        .anyMatch(p -> p.getCurrentTile() != null &&
            p.getCurrentTile().getTileId() == getBoard().getLastTile().getTileId());
  }

  @Override
  public Player getWinner() {
    return getPlayers().stream()
        .max((p1, p2) -> Integer.compare(p1.getPoints(), p2.getPoints()))
        .orElse(null);
  }

  @Override
  public void playOneTurn() {
    for (Player player : getPlayers()) {

      if (isFinished()) return;

      if (player.isSkipNextTurn()) {
        player.setSkipNextTurn(false);
        continue;
      }

      int steps = getDice().roll();
      player.move(steps);
      Tile tile = player.getCurrentTile();

      if (tile.getAction() != null) {
        tile.getAction().perform(player);
      }

      if (tile.getAction() instanceof ModifyPointsAction action) {
        player.addPoints(action.getPoints());
      } else if (tile.getAction() instanceof ModifyPointsAction action) {
        player.addPoints(-action.getPoints());
      }


      notifyObservers(BoardGameEvent.ROUND_PLAYED); 

    }
  }
}
