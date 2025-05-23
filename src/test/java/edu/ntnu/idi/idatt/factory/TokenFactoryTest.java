package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.game.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenFactoryTest {

  @Test
  void testFromIconCreatesTokenWithCorrectNameAndFile() {
    Token token = TokenFactory.fromIcon("flower");

    assertEquals("Flower", token.getName());
    assertEquals("flower.png", token.getIconFileName());
  }

  @Test
  void testFromIconCapitalizesCorrectly() {
    Token token = TokenFactory.fromIcon("StAr");
    assertEquals("Star", token.getName());
    assertEquals("StAr.png", token.getIconFileName());
  }

  @Test
  void testFromIconThrowsOnNull() {
    assertThrows(IllegalArgumentException.class, () -> TokenFactory.fromIcon(null));
  }

  @Test
  void testFromIconThrowsOnBlank() {
    assertThrows(IllegalArgumentException.class, () -> TokenFactory.fromIcon("   "));
  }
}
