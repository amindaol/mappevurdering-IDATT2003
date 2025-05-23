package edu.ntnu.idi.idatt.view.components;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;

/**
 * A horizontal container that displays multiple dice using {@link DieComponent}.
 * Provides methods to update the dots on individual or all dice.
 * Used in the board view to visually show the result of a dice roll.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class DieContainer extends HBox {

  private static final List<DieComponent> dieComponents = new ArrayList<>();

  /**
   * Creates a DieContainer with the specified number of dice.
   *
   * @param dieAmount the number of dice to create
   */
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

  /**
   * Sets the number of dots on a specific die by index.
   *
   * @param dieIndex index of the die to update
   * @param count number of dots to show
   * @throws IndexOutOfBoundsException if the index is invalid
   * @throws IllegalArgumentException if the count is not between 1 and 6
   */
  public void setDotsByDieIndex(int dieIndex, int count) {
    if (dieIndex < 0 || dieIndex >= dieComponents.size()) {
      throw new IndexOutOfBoundsException("Die index out of bounds");
    }
    if (count < 1 || count > 6) {
      throw new IllegalArgumentException("Dot count must be between 1 and 6.");
    }
    dieComponents.get(dieIndex).setDots(count);
  }

  /**
   * Sets the number of dots on all dice at once.
   *
   * @param counts list of dot counts, one per die
   * @throws IllegalArgumentException if the list size does not match number of dice
   * @throws IllegalArgumentException if any count is not between 1 and 6
   */
  public void setDotsAllDice(List<Integer> counts) {
    if (counts.size() != dieComponents.size()) {
      throw new IllegalArgumentException("Counts list size must match the number of dice");
    }
    for (int i = 0; i < counts.size(); i++) {
      int count = counts.get(i);
      if (count < 1 || count > 6) {
        throw new IllegalArgumentException("Dot count must be between 1 and 6 for die " + (i + 1));
      }
      setDotsByDieIndex(i, count);
    }
  }
}
