import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.SquareReversi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the model of Square Reversi. (instantiation, creation, and behavior)
 */
public class ExamplesSquareTests {
  SquareReversi game;

  @Before
  public void setup() {
    game = new SquareReversi(8);
  }

  // Demonstrating starting a game with the correct initial layout
  @Test
  public void testStartGameWithInitialLayout() {
    game.startGame();
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(4, 4)));
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(5, 5)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(4, 5)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(5, 4)));
  }

  // Demonstrating placing a black tile on the board and flipping the white tile
  @Test
  public void testPlaceBlackTileAndFlip() {
    game.startGame();
    game.skipTurn();
    game.placeTile(new CubicPosn(5, 6));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(5, 6)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(5, 5)));
  }

  // Demonstrating placing a white tile on the board and flipping the black tile
  @Test
  public void testPlaceWhiteTileAndFlip() {
    game.startGame();
    game.placeTile(new CubicPosn(6, 4));
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(5, 4)));
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(6, 4)));
  }

  // Demonstrating placing a black tile in a position which causes two directions to flip
  @Test
  public void testPlaceWithDoubleFlipDifferentDirection() {
    game.startGame();
    game.placeTile(new CubicPosn(4, 6));
    game.placeTile(new CubicPosn(3, 4));
    game.placeTile(new CubicPosn(3, 3));
    game.placeTile(new CubicPosn(5, 6));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(4, 5)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(5, 5)));
  }

  // Demonstrating placing a tiles in a position which causes flip in tiles in the same direction
  @Test
  public void testPlaceWithDoubleFlipSameDirection() {
    game.startGame();
    game.placeTile(new CubicPosn(4, 6));
    game.placeTile(new CubicPosn(3, 4));
    game.skipTurn();
    game.placeTile(new CubicPosn(4, 7));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(4, 6)));
    assertEquals(DiskState.BLACK, game.getBoard().get(new CubicPosn(4, 5)));
  }

  // tests that a game is not over
  @Test
  public void testGameNotOver() {
    game.startGame();
    assertFalse(game.isGameOver());
  }

  // tests that a game is over
  @Test
  public void testGameOver() {
    SquareReversi game = new SquareReversi(4);
    game.startGame();
    game.placeTile(new CubicPosn(2, 4));
    game.placeTile(new CubicPosn(1, 4));
    game.placeTile(new CubicPosn(1, 3));
    game.placeTile(new CubicPosn(1, 2));
    game.placeTile(new CubicPosn(1, 1));
    game.placeTile(new CubicPosn(3, 1));
    game.placeTile(new CubicPosn(2, 1));
    game.placeTile(new CubicPosn(3, 4));
    game.placeTile(new CubicPosn(4, 4));
    game.placeTile(new CubicPosn(4, 3));
    game.placeTile(new CubicPosn(4, 2));
    game.placeTile(new CubicPosn(4, 1));
    assertTrue(game.isGameOver());
  }
}