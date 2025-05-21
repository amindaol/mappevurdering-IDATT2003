package edu.ntnu.idi.idatt.ui.view.components;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;

public class DieContainer extends HBox {

  private static final List<DieComponent> dieComponents = new ArrayList<>();

  public DieContainer(int dieAmount) {
    this.setSpacing(10);

    for (int i = 0; i < dieAmount; i++) {
      DieComponent die = new DieComponent();
      die.maxWidth(100);
      die.maxHeight(100);
      this.getChildren().add(die);
      dieComponents.add(die);
    }
  }

  public void setDotsByDieIndex(int dieIndex, int count) {
    if (dieIndex < 0 || dieIndex >= dieComponents.size()) {
      throw new IndexOutOfBoundsException("Die index out of bounds");
    }
    dieComponents.get(dieIndex).setDots(count);
  }

  public void setDotsAllDice(List<Integer> counts) {
    if (counts.size() != dieComponents.size()) {
      throw new IllegalArgumentException("Counts list size must match the number of dice");
    }
    for (int i = 0; i < counts.size(); i++) {
      setDotsByDieIndex(i, counts.get(i));
    }
  }
}
