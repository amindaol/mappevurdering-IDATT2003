package edu.ntnu.idi.idatt.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player player;
  private final Token token = new Token("heart", "heart.png");
  private final LocalDate birthday = LocalDate.of(2000, 1, 1);

  @BeforeEach
  void setUp() {
    player = new Player("Aminda", token, birthday);
  }

  @Test
  void testConstructorStoresValues() {
    assertEquals("Aminda", player.getName());
    assertEquals(token, player.getToken());
    assertEquals(birthday, player.getBirthday());
    assertEquals(0, player.getPoints());
    assertFalse(player.isSkipNextTurn());
    assertNull(player.getCurrentTile());
  }

  @Test
  void testAddPointsAddsCorrectly() {
    player.addPoints(10);
    player.addPoints(-4);
    assertEquals(6, player.getPoints());
  }

  @Test
  void testSkipNextTurnFlag() {
    assertFalse(player.isSkipNextTurn());
    player.setSkipNextTurn(true);
    assertTrue(player.isSkipNextTurn());
  }

  @Test
  void testPlaceOnTileSetsTile() {
    Tile tile = new Tile(5, 0, 0);
    player.placeOnTile(tile);
    assertEquals(tile, player.getCurrentTile());
  }

  @Test
  void testPlaceOnTileThrowsIfNull() {
    assertThrows(NullPointerException.class, () -> player.placeOnTile(null));
  }

  @Test
  void testSetTokenUpdatesToken() {
    Token newToken = new Token("moon", "moon.png");
    player.setToken(newToken);
    assertEquals("moon.png", player.getToken().getIconFileName());
  }

  @Test
  void testSetTokenThrowsIfNull() {
    assertThrows(NullPointerException.class, () -> player.setToken(null));
  }

  @Test
  void testSetCurrentTileDirectly() {
    Tile tile = new Tile(7, 1, 1);
    player.setCurrentTile(tile);
    assertEquals(tile, player.getCurrentTile());
  }

  @Test
  void testToStringContainsNameAndTileInfo() {
    Tile tile = new Tile(3, 0, 1);
    player.placeOnTile(tile);
    String output = player.toString();
    assertTrue(output.contains("Aminda"));
    assertTrue(output.contains("3"));
  }
}
