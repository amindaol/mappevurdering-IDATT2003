package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;
import edu.ntnu.idi.idatt.model.action.TileAction;
import edu.ntnu.idi.idatt.model.game.Tile;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * A JavaFX component that visually represents a {@link Tile} on the game board.
 * Displays the tile's ID or point effect depending on the {@link TileAction}.
 * Positive points show as green, negative as red, and normal tiles as gray.
 * Used in the board layout to show tile state and value at a glance.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class TileComponent extends StackPane {

  private final Tile tile;

  /**
   * Creates a visual component for the given tile.
   * The tile's color and label are based on its action.
   *
   * @param tile the tile to visualize
   */
  public TileComponent(Tile tile) {
    int tileSize = 60;
    Rectangle background = new Rectangle(tileSize, tileSize);
    Text label = new Text(String.valueOf(tile.getTileId()));
    this.tile = tile;

    TileAction action = tile.getAction();
    if (action instanceof ModifyPointsAction add) {
      background.setFill(Color.LIGHTGREEN);
      label.setText("+" + add.getPoints());
    } else if (action instanceof ModifyPointsAction remove) {
      background.setFill(Color.SALMON);
      label.setText("-" + remove.getPoints());
    } else {
      background.setFill(Color.LIGHTGRAY);
    }

    background.setStroke(Color.DARKGRAY);
    setAlignment(Pos.CENTER);
    getChildren().addAll(background, label);
  }

  /**
   * Returns the tile associated with this component.
   *
   * @return the tile
   */
  public Tile getTile() {
    return tile;
  }
}
