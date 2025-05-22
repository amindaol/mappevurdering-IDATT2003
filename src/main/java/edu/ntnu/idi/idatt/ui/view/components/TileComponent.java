package edu.ntnu.idi.idatt.ui.view.components;

import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.model.action.TileAction;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileComponent extends StackPane {

  private final Rectangle background;
  private final Text label;
  private final int tileSize = 60 ;
  private final Tile tile;

  public TileComponent(Tile tile) {
    this.background = new Rectangle(tileSize, tileSize);
    this.label = new Text(String.valueOf(tile.getTileId()));
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

  public Tile getTile() {
    return tile;
  }
}
