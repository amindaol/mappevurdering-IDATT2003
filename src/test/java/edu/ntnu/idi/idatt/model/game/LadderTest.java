package edu.ntnu.idi.idatt.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LadderTest {

  @Test
  void testConstructorStoresValuesCorrectly() {
    Ladder ladder = new Ladder(3, 14);
    assertEquals(3, ladder.getFromTileId());
    assertEquals(14, ladder.getToTileId());
  }

  @Test
  void testToStringIsFormattedCorrectly() {
    Ladder ladder = new Ladder(7, 2);
    String expected = "Ladder{7 → 2}";
    assertEquals(expected, ladder.toString());
  }
}
