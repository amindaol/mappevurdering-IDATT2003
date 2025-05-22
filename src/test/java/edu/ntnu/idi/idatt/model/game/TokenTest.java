package edu.ntnu.idi.idatt.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

  @Test
  void testConstructorStoresValuesCorrectly() {
    Token token = new Token("flower", "flower.png");
    assertEquals("flower", token.getName());
    assertEquals("flower.png", token.getIconFileName());
  }

  @Test
  void testConstructorAllowsNullIconFileName() {
    Token token = new Token("star", null);
    assertEquals("star", token.getName());
    assertNull(token.getIconFileName());
  }

  @Test
  void testConstructorThrowsIfNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Token(null, "icon.png"));
  }

  @Test
  void testToStringIncludesIconFileNameIfPresent() {
    Token token = new Token("cloud", "cloud.png");
    String str = token.toString();
    assertTrue(str.contains("cloud"));
    assertTrue(str.contains("cloud.png"));
  }

  @Test
  void testToStringOmitsBracketsIfIconFileNameIsNull() {
    Token token = new Token("sparkle", null);
    String str = token.toString();
    assertEquals("sparkle", str);
  }
}
