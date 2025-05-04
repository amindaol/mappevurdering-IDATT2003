package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.view.components.LaddersBoard;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;

public class BoardView {

  private final BorderPane root;
  private final LaddersBoard board;


  public BoardView(int width, int height) {
    this.root = new BorderPane();
    this.board = new LaddersBoard(width, height);

    root.setCenter(board.getGrid());


  }

  public Parent getRoot() {
    return root;
  }
}
