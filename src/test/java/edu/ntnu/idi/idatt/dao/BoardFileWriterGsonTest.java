package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;
import edu.ntnu.idi.idatt.game.LadderAction;
import edu.ntnu.idi.idatt.game.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BoardFileWriterGsonTest {

  @TempDir
  Path tempDir;
  private Path jsonFile;
  private BoardFileWriterGson writer;
  private BoardFileReaderGson reader;

  @BeforeEach
  void setUp() {
    jsonFile = tempDir.resolve("board.json");
    writer = new BoardFileWriterGson();
    reader = new BoardFileReaderGson();
  }

  @Test
  void testWriteAndReadRoundTrip() throws Exception {
    Board board = new Board();
    Tile t1 = new Tile(1);
    Tile t2 = new Tile(2);
    Tile t3 = new Tile(3);
    board.addTile(t1);
    board.addTile(t2);
    board.addTile(t3);
    t1.setNextTile(t2);
    t2.setLandAction(new LadderAction(3, "Up to 3"));

    writer.writeBoard(jsonFile, board);
    assertTrue(Files.exists(jsonFile), "JSON file should exist after write");

    Board loaded = reader.readBoard(jsonFile);
    assertEquals(3, loaded.size());
    assertTrue(loaded.getTile(2).getLandAction() instanceof LadderAction);
    LadderAction la = (LadderAction) loaded.getTile(2).getLandAction();
    assertEquals(3, la.getDestinationTileId());
    assertEquals("Up to 3", la.getDescription());
  }
}