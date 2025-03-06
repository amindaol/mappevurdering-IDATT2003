package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.game.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

  private Die die;

  @BeforeEach
  void setUp() {
    die = new Die();
  }

  @Test
  void roll_returnValueOneToSix() {
    int value = die.roll();
    assertTrue(value >= 1 && value <= 6);
  }

  @Test
  void getValue_returnMostRecentValue() {

  }

}
