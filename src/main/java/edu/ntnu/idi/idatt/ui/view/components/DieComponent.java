package edu.ntnu.idi.idatt.ui.view.components;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class DieComponent extends GridPane {

  public DieComponent() {
    this.setPrefSize(60, 60);
    this.getStyleClass().add("die");
    this.setHgap(5);
    this.setVgap(5);
    this.setAlignment(Pos.CENTER);
    this.setupGrid();
    this.drawDots(4);
  }

  public void setDots(int count) {
    this.drawDots(count);
  }

  private void setupGrid() {
    for (int row = 0; row < 3; row++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPercentHeight(33.33);
      getRowConstraints().add(rowConstraints);
    }

    for (int col = 0; col < 3; col++) {
      ColumnConstraints colConstraints = new ColumnConstraints();
      colConstraints.setPercentWidth(33.33);
      getColumnConstraints().add(colConstraints);
    }
  }


  private void drawDots(int count) {
    getChildren().clear();
    List<Point2D> dotPositions = getDotPositions(count);

    for (Point2D pos : dotPositions) {
      Circle dot = new Circle(6);
      dot.getStyleClass().add("die-dot");
      StackPane stackPane = new StackPane(dot);
      add(stackPane, (int) pos.getX(), (int) pos.getY());
    }
  }


  private List<Point2D> getDotPositions(int count) {
    return switch (count) {
      case 1 -> List.of(center());
      case 2 -> List.of(topLeft(), bottomRight());
      case 3 -> List.of(topLeft(), center(), bottomRight());
      case 4 -> List.of(topLeft(), topRight(), bottomLeft(), bottomRight());
      case 5 -> List.of(topLeft(), topRight(), center(), bottomLeft(), bottomRight());
      case 6 -> List.of(
          topLeft(), topRight(), middleLeft(), middleRight(), bottomLeft(), bottomRight());
      default -> throw new IllegalArgumentException("Invalid die count: " + count);
    };
  }

  // Dot positions for a 3x3 grid
  private Point2D topLeft() {
    return new Point2D(0, 0);
  }

  private Point2D topRight() {
    return new Point2D(0, 2);
  }

  private Point2D middleLeft() {
    return new Point2D(1, 0);
  }

  private Point2D center() {
    return new Point2D(1, 1);
  }

  private Point2D middleRight() {
    return new Point2D(1, 2);
  }

  private Point2D bottomLeft() {
    return new Point2D(2, 0);
  }

  private Point2D bottomRight() {
    return new Point2D(2, 2);
  }
}
