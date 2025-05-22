package edu.ntnu.idi.idatt.model.game;

/**
 * Represents a ladder or a snake on the game board.
 * A ladder connects one tile to another, either moving the player up or down.
 *
 * Used for both ladders and snakes by interpreting direction.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Ladder {

  private final int fromTileId;
  private final int toTileId;

  /**
   * Creates a new ladder or snake from one tile to another.
   *
   * @param fromTileId the starting tile ID
   * @param toTileId the destination tile ID
   */
  public Ladder(int fromTileId, int toTileId) {
    this.fromTileId = fromTileId;
    this.toTileId = toTileId;
  }

  /**
   * Returns the starting tile ID.
   *
   * @return the from tile ID
   */
  public int getFromTileId() {
    return fromTileId;
  }

  /**
   * Returns the destination tile ID.
   *
   * @return the to tile ID
   */
  public int getToTileId() {
    return toTileId;
  }

  /**
   * Returns a string representation of the ladder or snake.
   *
   * @return a string like "Ladder{5 → 12}"
   */
  @Override
  public String toString() {
    return "Ladder{" + fromTileId + " → " + toTileId + '}';
  }

}
