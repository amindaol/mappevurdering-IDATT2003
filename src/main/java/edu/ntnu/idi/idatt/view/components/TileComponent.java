package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.model.game.action.AddPointsAction;
import edu.ntnu.idi.idatt.model.game.action.RemovePointsAction;
import edu.ntnu.idi.idatt.model.game.action.TileAction;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileComponent extends StackPane {

  private final Rectangle background;
  private final Text label;
  private final int tileSize = 60 ;

  public TileComponent(Tile tile) {
    this.background = new Rectangle(tileSize, tileSize);
    this.label = new Text(String.valueOf(tile.getTileId()));

    TileAction action = tile.getAction();
    if (action instanceof AddPointsAction add) {
      background.setFill(Color.LIGHTGREEN);
      label.setText("+" + add.getPoints());
    } else if (action instanceof RemovePointsAction remove) {
      background.setFill(Color.SALMON);
      label.setText("-" + remove.getPoints());
    } else {
      background.setFill(Color.LIGHTGRAY);
    }

    background.setStroke(Color.DARKGRAY);
    getChildren().addAll(background, label);
  }
}
