package cs3500.reversi;

import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for surface level clear testing of our Model, simple functionality.
 */
public class ExamplesTests {
  Reversi game;

  @Before
  public void setup() {
    game = new Reversi();
  }

  // Demonstrating starting a game with the correct initial layout
  @Test
  public void testStartGameWithInitialLayout() {
    game.startGame(4);
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(-1, 0)));
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(1, -1)));
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(0, 1)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(1, 0)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(0, -1)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(-1, 1)));
  }

  // Demonstrating placing a black tile on the board and flipping the white tile
  @Test
  public void testPlaceBlackTileAndFlip() {
    game.startGame(4);
    game.placeTile(new CubicPosn(2, -1), DiskState.BLACK);
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(2, -1)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(1, 0)));
  }

  // Demonstrating placing a white tile on the board and flipping the black tile
  @Test
  public void testPlaceWhiteTileAndFlip() {
    game.startGame(4);
    game.placeTile(new CubicPosn(-1, 2), DiskState.WHITE);
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(-1, 2)));
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(0, 1)));
  }

  // Demonstrating placing a black tile in a position which causes two directions to flip
  @Test
  public void testPlaceWithDoubleFlipDifferentDirection() {
    game.startGame(4);
    game.placeTile(new CubicPosn(2, -1), DiskState.BLACK);
    game.placeTile(new CubicPosn(1, -2), DiskState.WHITE);
    game.placeTile(new CubicPosn(-1, -1), DiskState.BLACK);
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(-1, 0)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(0, -1)));
  }

  // Demonstrating placing a tiles in a position which causes flip in tiles in the same direction
  @Test
  public void testPlaceWithDoubleFlipSameDirection() {
    game.startGame(4);
    game.placeTile(new CubicPosn(2, -1), DiskState.WHITE);
    game.placeTile(new CubicPosn(3, -1), DiskState.BLACK);
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(1, -1)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(2, -1)));
  }

  // tests that a game is not over
  @Test
  public void testGameNotOver() {
    game.startGame(2);
    assertFalse(game.isGameOver());
  }

  // tests that a game is over
  @Test
  public void testGameOver() {
    game.startGame(2);
    game.placeTile(new CubicPosn(1, -2), DiskState.BLACK);
    game.placeTile(new CubicPosn(2, -1), DiskState.WHITE);
    game.placeTile(new CubicPosn(1, 1), DiskState.BLACK);
    game.placeTile(new CubicPosn(-1, 2), DiskState.WHITE);
    game.placeTile(new CubicPosn(-2, 1), DiskState.BLACK);
    game.placeTile(new CubicPosn(-1, -1), DiskState.WHITE);
    assertTrue(game.isGameOver());
  }
}
